package com.kolpakov.githubrepositories.ui;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kolpakov.githubrepositories.R;
import com.kolpakov.githubrepositories.data.models.Repository;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pkolpakov on 05.03.2017.
 */

public class RepositoriesAdapter extends RecyclerView.Adapter<RepositoriesAdapter.RepositoryViewHolder> {
    private LayoutInflater inflater;
    private List<Repository> repositoryList = new ArrayList<>();
    private OnLoadMoreListener loadMoreListener;
    private Picasso picasso;

    public RepositoriesAdapter(Context context) {
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        picasso =Picasso.with(context);
        setHasStableIds(true);
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).getId();
    }

    @Override
    public RepositoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView item = (CardView) inflater.inflate(R.layout.repository_item, parent, false);
        RepositoryViewHolder vh = new RepositoryViewHolder(item);
        return vh;
    }

    @Override
    public void onBindViewHolder(RepositoryViewHolder holder, int position) {
        Repository repository = getItem(position);
        holder.tvName.setText(repository.getFullName());
        holder.tvDescription.setText(repository.getDescription());
        picasso.load(repository.getOwner().getAvatarUrl()).error(R.drawable.ic_person).into(holder.avatar);
    }

    public void setRepositoryList(List<Repository> repositoryList) {
        this.repositoryList = repositoryList;
        if(this.repositoryList == null)
            this.repositoryList = new ArrayList<>();
        notifyDataSetChanged();
    }

    private Repository getItem(int position){
        if(position >repositoryList.size()-5 && loadMoreListener != null){
            loadMoreListener.loadMore();
        }
        return repositoryList.get(position);
    }

    public void setLoadMoreListener(OnLoadMoreListener loadMoreListener) {
        this.loadMoreListener = loadMoreListener;
    }

    @Override
    public int getItemCount() {
        return repositoryList.size();
    }

    static class RepositoryViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        ImageView avatar;
        TextView tvDescription;
        public RepositoryViewHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.name);
            avatar = (ImageView) itemView.findViewById(R.id.avatar);
            tvDescription = (TextView) itemView.findViewById(R.id.description);
        }
    }
    public interface OnLoadMoreListener{
        void loadMore();
    }
}
