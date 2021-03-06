package gitmad.bitter.ui;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Date;
import java.util.List;

import gitmad.bitter.R;
import gitmad.bitter.data.CommentProvider;
import gitmad.bitter.data.PostProvider;
import gitmad.bitter.data.UserProvider;
import gitmad.bitter.model.Post;

/**
 * Adapter for displaying cardviews that present posts in a RecyclerView.
 */
public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {

    private List<Post> posts;
    private PostProvider postProvider;
    private UserProvider userProvider;
    private CommentProvider commentProvider;
    private long time;

    private FeedInteractionListener listener;

    public PostAdapter(List<Post> posts, FeedInteractionListener pListener,
                       PostProvider postProvider, UserProvider userProvider,
                       CommentProvider commentProvider) {
        this.posts = posts;
        listener = pListener;
        this.postProvider = postProvider;
        this.userProvider = userProvider;
        this.commentProvider = commentProvider;
        time = new Date().getTime();
    }

    public void add(Post p) {
        posts.add(0, p);
        this.notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.view_post, viewGroup, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int i) {
        viewHolder.postText.setText(posts.get(i).getText());
        viewHolder.userText.setText(userProvider.getAuthorOfPost(posts.get(i)
                ).getName());
        viewHolder.downvoteText.setText(Integer.toString(posts.get(i).getDownvotes()));
        viewHolder.timeText.setText(getTime(posts.get(i)));
        viewHolder.repliesText.setText(Integer.toString(commentProvider.getCommentsOnPost(
                posts.get(i).getId()).length) + " replies");
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onPostClicked(posts.get(i), i);
            }
        });


        viewHolder.downvoteImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onDownvoteClicked(posts.get(i), i);
                Post p  = postProvider.getPost(posts.get(i).getId());
                posts.set(i, p);
                viewHolder.downvoteText.setText(Integer.toString(posts.get(i).getDownvotes()));
            }
        });
    }

    public String getTime(Post p) {
        Long sec = time - p.getTimestamp();
        int newTime = (int) (sec / 1000);
        if(newTime > 3600) {
            return "" + newTime/3600 + " hours";
        } else if (newTime > 60) {
            return "" + newTime/60 + " minutes";
        } else {
            return "" + newTime + " seconds";
        }
    }

    public void resetTime() {
        time = new Date().getTime();
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView userText;
        public TextView postText;
        public TextView timeText;
        public TextView repliesText;
        public ImageView downvoteImage;
        public TextView downvoteText;

        public ViewHolder(View postLayout) {
            super(postLayout);
            userText = (TextView) postLayout.findViewById(R.id.user_text);
            postText = (TextView) postLayout.findViewById(R.id.post_text);
            timeText = (TextView) postLayout.findViewById(R.id.time_posted);
            repliesText = (TextView) postLayout.findViewById(R.id.post_replies);

            downvoteImage = (ImageView) postLayout.findViewById(R.id.downvote_button);
            downvoteText = (TextView) postLayout.findViewById(R.id.downvote_counter);
        }
    }

    public interface FeedInteractionListener {
        void onPostClicked(Post p, int index);
        void onDownvoteClicked(Post p, int index);
    }
}
