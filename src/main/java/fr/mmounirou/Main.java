package fr.mmounirou;

import fr.mmounirou.trakt.api.requests.ListItemsRequest;
import fr.mmounirou.trakt.api.services.TraktListService;
import fr.mmounirou.trakt.api.utils.TraktItem;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import retrofit.RestAdapter;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {

    final static int minimumVotes = 25000;

    private final static Function<Element,ImdbSearchResult> imdbSearchMapper = elmt -> {
        String title = elmt.select("td a").text();
        String imdbId = elmt.select("td a").attr("href").split("/")[2];
        String rating = elmt.select("b").text();
        String votes = elmt.select("td").last().text().replace(",", ".");

        return new ImdbSearchResult(title, rating, imdbId, votes);
    };

    private final static  Function<Element,ImdbSearchResult> imdbChartMapper = x ->{
        String title = x.select("td.titleColumn a").text();
        String imdbId = x.select("td.titleColumn a").attr("href").split("/")[2];
        return new ImdbSearchResult(title,"",imdbId,"");
    };

    private final static Function<List<ImdbSearchResult>, List<ImdbSearchResult>> weightedRatingSorter = (list) -> {
        double averageRating = list
                .parallelStream()
                .mapToDouble(res -> res.getRating())
                .average()
                .getAsDouble();

        return list
                .parallelStream()
                .map(x -> {
                    x.weightedRating(averageRating, minimumVotes);
                    return x;
                })
                .sorted((y, x) -> x.getWeightedRating().compareTo(y.getWeightedRating()))
                        //.peek(x -> System.out.println(x.getImdbid() + " - " + x.getTitle() + " - " + x.getRating() + " - "+ x.getWeightedRating() + " - " + x.getVotes()))
                .collect(Collectors.toList());
    };

    public static void main(String[] args) throws IOException {
        LocalDateTime startDate = LocalDateTime.now();
        System.out.printf("Start update %s ...%n", startDate.toString());

        String apiKey = args[0];
        String username = args[1];
        String password = args[2];


        String top250UrlByYearFmt = "http://www.imdb.com/search/title?count=250&num_votes=%d,&release_date=%d,%d&sort=user_rating,desc&title_type=feature&view=simple";
        String top250TraktListFmt = "imdb-top-user-rated-movies-of-%d";
        String top250Url2014 = "http://www.imdb.com/search/title?count=250&release_date=2014,2014&title_type=feature&view=simple";

        List<ImdToTraktParams> paramsList = new ArrayList<>();
        IntStream.range(2012, 2014).forEach(year -> paramsList.add(new ImdToTraktParams(String.format(top250UrlByYearFmt, minimumVotes, year, year), String.format(top250TraktListFmt, year), "movie", weightedRatingSorter, ".results", imdbSearchMapper)));
        paramsList.add(new ImdToTraktParams(top250Url2014, "imdb-popular-250-movies-for-2014", "movie", Function.identity(), ".results", imdbSearchMapper));
        paramsList.add(new ImdToTraktParams("http://www.imdb.com/search/title?at=0&num_votes=10000,&sort=user_rating&title_type=tv_series", "imdb-highest-rated-tv-series", "show", Function.identity(), ".results", imdbSearchMapper));
        paramsList.add(new ImdToTraktParams("http://www.imdb.com/search/title?at=0&num_votes=10000,&sort=moviemeter&title_type=tv_series", "imdb-most-popular-tv-series", "show", Function.identity(), ".results", imdbSearchMapper));
        paramsList.add(new ImdToTraktParams("http://www.imdb.com/search/title?at=0&num_votes=10000,&sort=year,desc&title_type=tv_series", "imdb-latest-tv-series", "show", Function.identity(), ".results", imdbSearchMapper));
        paramsList.add(new ImdToTraktParams("http://www.imdb.com/chart/top?ref_=nv_ch_250_4", "imdb-best-250-movies", "movie", Function.identity(), ".chart", imdbChartMapper));


        boolean allUpdated = paramsList.parallelStream().map(x -> imdbToTrakt(username, password, apiKey, x)).allMatch(x -> x);
        LocalDateTime endDate = LocalDateTime.now();
        System.out.printf("All update done %s %s took %s %n", allUpdated, endDate.toString(), Duration.ofSeconds(endDate.toEpochSecond(ZoneOffset.UTC) - startDate.toEpochSecond(ZoneOffset.UTC)).toString());
    }

    private static boolean imdbToTrakt(String username, String password, String apiKey, ImdToTraktParams imdToTraktParams){
        System.out.printf("Start update of %s ... %n", imdToTraktParams.getSlug());
        try {
            RestAdapter restAdapter = new RestAdapter
                    .Builder()
                    .setLogLevel(RestAdapter.LogLevel.NONE)
                    .setEndpoint("http://api.trakt.tv/")
                    .build();

            TraktListService service = restAdapter.create(TraktListService.class);

            cleanTrackList(service, apiKey, username, imdToTraktParams.getSlug(), password);

            List<ImdbSearchResult> imdbResults = getImdbSearchResults(imdToTraktParams.getImdbUrl(), imdToTraktParams.getImdbMapper(), imdToTraktParams.getResultCssQuery());
            List<TraktItem> imdbSortedResults = imdToTraktParams.getSorter().apply(imdbResults)
                    .parallelStream()
                    .map(x -> {
                        TraktItem result = new TraktItem();
                        result.setType(imdToTraktParams.getItemtype());
                        result.setTitle(x.getTitle());
                        result.setImdb_id(x.getImdbid());
                        return result;
                    })
                    .collect(Collectors.toList());

            service.addItems(apiKey, new ListItemsRequest(username, password, imdToTraktParams.getSlug(), imdbSortedResults));
            System.out.printf("Update of %s done %n", imdToTraktParams.getSlug());

            return true;

        }catch (Throwable e){
            System.out.printf("fail update of %s ... %n", imdToTraktParams.getSlug());
            return false;
        }

    }
    private static void cleanTrackList(TraktListService service, String apiKey, String username, String slug, String password) {
        List<TraktItem> listContents = service
                .getItems(apiKey, username, slug)
                .getItems()
                .parallelStream()
                .map(x -> {
                    switch (x.getType()) {
                        case "show":
                            x.getShow().setType(x.getType());
                            return x.getShow();
                        case "movie":
                            x.getMovie().setType(x.getType());
                            return x.getMovie();
                        default:
                            throw new UnsupportedOperationException("unsupported item type " + x.getType());
                    }
                })
                .collect(Collectors.toList());

        service.deleteItems(apiKey, new ListItemsRequest(username, password, slug, listContents));
    }
    private static List<ImdbSearchResult> getImdbSearchResults(String searchUrl, Function<Element, ImdbSearchResult> imdbMapper, String resultCssQuery) {
        Document document = null;
        try {
            document = Jsoup.connect(searchUrl).userAgent("Chrome/33.0.1750.117").get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return document
                .select(resultCssQuery)
                .select("tr.odd,tr.even")
                .parallelStream()
                        //.peek(e -> System.out.println(e))
                .map(imdbMapper)
                .collect(Collectors.toList());
    }

    private static class ImdToTraktParams {
        private final String imdbUrl;
        private final String slug;
        private final String itemtype;
        private final Function<List<ImdbSearchResult>, List<ImdbSearchResult>> sorter;
        private final String resultCssQuery;
        private final Function<Element, ImdbSearchResult> imdbMapper;

        private ImdToTraktParams(String imdbUrl, String slug, String itemtype, Function<List<ImdbSearchResult>, List<ImdbSearchResult>> sorter, String resultCssQuery, Function<Element, ImdbSearchResult> imdbMapper) {
            this.imdbUrl = imdbUrl;
            this.slug = slug;
            this.itemtype = itemtype;
            this.sorter = sorter;
            this.resultCssQuery = resultCssQuery;
            this.imdbMapper = imdbMapper;
        }

        public String getImdbUrl() {
            return imdbUrl;
        }

        public String getSlug() {
            return slug;
        }

        public String getItemtype() {
            return itemtype;
        }

        public Function<List<ImdbSearchResult>, List<ImdbSearchResult>> getSorter() {
            return sorter;
        }

        public String getResultCssQuery() {
            return resultCssQuery;
        }

        public Function<Element, ImdbSearchResult> getImdbMapper() {
            return imdbMapper;
        }
    }
}
