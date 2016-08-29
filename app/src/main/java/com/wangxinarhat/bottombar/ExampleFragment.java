package com.wangxinarhat.bottombar;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wangxinarhat.bottombar.proxy.DynamicProxy;
import com.wangxinarhat.bottombar.proxy.IToolbarConfig;
import com.wangxinarhat.bottombar.proxy.ToolbarConfigEat;
import com.wangxinarhat.bottombar.proxy.ToolbarConfigFavorite;
import com.wangxinarhat.bottombar.proxy.ToolbarConfigMine;
import com.wangxinarhat.bottombar.proxy.ToolbarConfigNearby;
import com.wangxinarhat.bottombar.proxy.ToolbarConfigRecent;

import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wang on 2016/8/13.
 */
public class ExampleFragment extends Fragment {


    @BindView(R.id.text)
    TextView mText;

     @BindView(R.id.recycler)
     RecyclerView mRecycler;


    private int mType;
    private String[] tabs;

    public static Fragment newInstance(int type) {
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);


        Fragment fragment = new ExampleFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.frag_example, container, false);
        ButterKnife.bind(this, root);


        setMenuVisibility(true);//toolbar menu is visiable? default true
        return root;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mType = getArguments().getInt("type");
        tabs = getResources().getStringArray(R.array.tab_strs);
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        if (0 == mType) {
            inflater.inflate(R.menu.menu_home, menu);
        }
        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action1:
                showMsg("action1 selected");
                break;
            case R.id.action2:
                showMsg("action2 selected");
                break;
            case R.id.action3:
                showMsg("action3 selected");
                break;
        }
        return true;
    }

    private void showMsg(String msg) {
        CommonUtils.showShortSnackbar(getView(), msg);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        barConfig();

        bindData();
    }

    private void bindData() {
        mRecycler.setLayoutManager(new LinearLayoutManager(mRecycler.getContext()));
        mRecycler.setAdapter(new SimpleStringRecyclerViewAdapter(getActivity(), getRandomSublist(Cheeses.sCheeseStrings, 30)));

    }

    private List<String> getRandomSublist(String[] array, int amount) {
        ArrayList<String> list = new ArrayList<>(amount);
        Random random = new Random();
        while (list.size() < amount) {
            list.add(array[random.nextInt(array.length)]);
        }
        return list;
    }


    private void barConfig() {

        IToolbarConfig config = null;
        String toolbarTitle = tabs[mType];
        switch (mType) {
            case 0:
                config = new ToolbarConfigRecent();
                break;
            case 1:
                config = new ToolbarConfigFavorite();
                break;
            case 2:
                config = new ToolbarConfigNearby();
                break;
            case 3:
                config = new ToolbarConfigMine();
                break;
            case 4:
                config = new ToolbarConfigEat();
                break;

        }


        DynamicProxy proxy = new DynamicProxy(config);
        ClassLoader loader = config.getClass().getClassLoader();
        IToolbarConfig iToolbarConfig = (IToolbarConfig) Proxy.newProxyInstance(loader, new Class[]{IToolbarConfig.class}, proxy);
        iToolbarConfig.toolbarConfig((AppCompatActivity) getActivity(), toolbarTitle);
        mText.setText(toolbarTitle);

    }

    public static class SimpleStringRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleStringRecyclerViewAdapter.ViewHolder> {

        private final TypedValue mTypedValue = new TypedValue();
        private int mBackground;
        private List<String> mValues;

        public static class ViewHolder extends RecyclerView.ViewHolder {
            public String mBoundString;

            public final View mView;
            public final ImageView mImageView;
            public final TextView mTextView;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                mImageView = (ImageView) view.findViewById(R.id.avatar);
                mTextView = (TextView) view.findViewById(android.R.id.text1);
            }

            @Override
            public String toString() {
                return super.toString() + " '" + mTextView.getText();
            }
        }

        public String getValueAt(int position) {
            return mValues.get(position);
        }

        public SimpleStringRecyclerViewAdapter(Context context, List<String> items) {
            context.getTheme().resolveAttribute(R.attr.selectableItemBackground, mTypedValue, true);
            mBackground = mTypedValue.resourceId;
            mValues = items;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item, parent, false);
            view.setBackgroundResource(mBackground);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.mBoundString = mValues.get(position);
            holder.mTextView.setText(mValues.get(position));



            Glide.with(holder.mImageView.getContext())
                    .load(Cheeses.getRandomCheeseDrawable())
                    .fitCenter()
                    .into(holder.mImageView);
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }
    }


}
