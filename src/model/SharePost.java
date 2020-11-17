package model;

public class SharePost {
    private String postid;
    private String mediaid;
    private SharePost sharepost;
    private Media media;

    public SharePost(String postid, String mediaid, SharePost sharepost, Media media) {
        this.postid = postid;
        this.mediaid = mediaid;
        this.sharepost = sharepost;
        this.media = media;
        }

        public String getPostid() {
            return postid;
        }

        public String getMediaid() {
            return mediaid;
        }

        public SharePost getSharepost() {
            return sharepost;
        }

        public Media getMedia() {
            return media;
        }
}
