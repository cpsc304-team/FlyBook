package model;

public class GroupMemberAdministrator {
     private String uid;
     private String nickname;
     private User user;

     public GroupMemberAdministrator(String uid, String nickname, User user) {
          this.uid = uid;
          this.nickname = nickname;
          this.user = user;
     }

     public String getUid() {
          return uid;
     }

     public String getNickname() {
          return nickname;
     }

     public User getUser() {
          return user;
     }
}

