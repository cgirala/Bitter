package gitmad.bitter.model;


/**
 * Models a user
 */
public class User {
    private String name;
    private String id;
    private int posts;
    private int salt;
    private int totalVotes;

    public User(String pName, String pId) {
        name = pName;
        id = pId;
        posts = 0;
        salt = 0;
        totalVotes = 0;
    }

    public User() {
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public void setTotalPosts(int numPosts) { posts = numPosts; }

    public void setSalt(int numSalt) { salt = numSalt; }

    public void setTotalVotes( int numVotes) { totalVotes = numVotes; }

    public int getPosts() { return posts; }

    public int getSalt() { return salt; }

    public int getTotalVotes() { return totalVotes; }

    @Override
    public int hashCode() {
        return getId().hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof User)) {
            return false;
        }

        User other = (User) o;

        return other.getId().equals(getId());
    }
}
