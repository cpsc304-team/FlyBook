package model;


/*CREATE TABLE Group_Member
    (nickname CHAR(10),
    u_id CHAR(10),
    PRIMARY KEY (u_id),
    FOREIGN KEY (u_id) REFERENCES User_info);

CREATE TABLE Group_administrator
    (nickname CHAR(10),
    u_id CHAR(10),
    PRIMARY KEY (u_id),
    FOREIGN KEY (u_id) REFERENCES User_info);*/


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

