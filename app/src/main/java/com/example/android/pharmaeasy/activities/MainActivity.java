package com.example.android.pharmaeasy.activities;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.android.pharmaeasy.R;
import com.example.android.pharmaeasy.adapter.PageAdapter;
import com.example.android.pharmaeasy.model.Page;
import com.example.android.pharmaeasy.model.PageResponse;
import com.example.android.pharmaeasy.utils.ApiClient;
import com.example.android.pharmaeasy.utils.ApiService;
import com.example.android.pharmaeasy.utils.EndlessRecyclerOnScrollListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    PageAdapter mAdapter;
    LinearLayoutManager linearLayoutManager;

    @BindView(R.id.page_recycler_view)
    RecyclerView listRecyclerView;

    @BindView(R.id.progressbar)
    ProgressBar progressBar;

    @BindView(R.id.next_page_loading_progressBar)
    ProgressBar next_page_loading_progressBar;

    private static final int PAGE_START = 1;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int totalPage = 0;
    private int currentPage = PAGE_START;

    private ApiService apiService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        mAdapter = new PageAdapter(this);

        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        listRecyclerView.setLayoutManager(linearLayoutManager);

        listRecyclerView.setItemAnimator(new DefaultItemAnimator());

        listRecyclerView.setAdapter(mAdapter);
        listRecyclerView.setHasFixedSize(true);

        listRecyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener(linearLayoutManager) {
            @Override
            protected void loadMoreItems() {

                isLoading = true;
                currentPage += 1;

                // mocking network delay for API call
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Log.d(TAG ,"Before loadNextPage calling...");
                        next_page_loading_progressBar.setVisibility(View.VISIBLE);
                        loadNextPage();
                    }
                }, 1000);
            }

            @Override
            public int getTotalPageCount() {
                return totalPage;
            }

            @Override
            public boolean isLastPage() {
                return isLastPage;
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }
        });

        //init service and load data
        apiService = ApiClient.getClient().create(ApiService.class);

        loadFirstPage();

    }

    private void loadFirstPage() {

        Log.d(TAG, "Inside loadFirstPage...");

        callPageApi().enqueue(new Callback<PageResponse>() {
            @Override
            public void onResponse(Call<PageResponse> call, Response<PageResponse> response) {

                if(response.isSuccessful()) {

                    List<Page> results = fetchData(response);
                    totalPage = response.body().getTotalPages();

                    if(! results.isEmpty() && results.size() > 0) {

                        progressBar.setVisibility(View.GONE);
                        mAdapter.addAll(results);

                        if (currentPage <= response.body().getTotalPages()) {

                            mAdapter.addLoadingFooter();
                        } else {
                            isLastPage = true;
                        }
                    }
                }
            }


            @Override
            public void onFailure(Call<PageResponse> call, Throwable t) {
                Log.d(TAG , " First page loading error message : " + t.getMessage());
                Toast.makeText(MainActivity.this, "Loading error: "+ t.getMessage(), Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);

            }
        });
    }

    private void loadNextPage() {
        Log.d(TAG , "Inside loadNextPage....");

        callPageApi().enqueue(new Callback<PageResponse>() {
            @Override
            public void onResponse(Call<PageResponse> call, Response<PageResponse> response) {
                if(response.isSuccessful()) {

                    List<Page> results = fetchData(response);
                    totalPage = response.body().getTotalPages();

                    if(! results.isEmpty() && results.size() > 0) {
                        mAdapter.removeLoadingFooter();
                        isLoading = false;

                        mAdapter.addAll(results);

                        if (currentPage != response.body().getTotalPages()) {

                            mAdapter.addLoadingFooter();
                        } else {

                            isLastPage = true;
                        }
                        next_page_loading_progressBar.setVisibility(View.GONE);
                    }
                }

            }

            @Override
            public void onFailure(Call<PageResponse> call, Throwable t) {
                Log.d(TAG , " Second page loading error message : " + t.getMessage());
                Toast.makeText(MainActivity.this, "Loading error: "+ t.getMessage(), Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
                next_page_loading_progressBar.setVisibility(View.GONE);

            }
        });
    }


    /**
     * @param response extracts List<{@link Page>} from response
     * @return
     */
    private List<Page> fetchData(Response<PageResponse> response) {
        PageResponse pageResult = response.body();
        return pageResult.getData();
    }


        /**
         * Performs a Retrofit call to the page API.
         * Same API call for Pagination.
         * As {@link #currentPage} will be incremented automatically
         * by @{@link EndlessRecyclerOnScrollListener} to load next page.
         */
    private Call<PageResponse> callPageApi() {
        return apiService.getPageDetails(
                currentPage
        );
    }
}



