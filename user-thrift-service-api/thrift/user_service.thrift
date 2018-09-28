namespace java com.jj.fly.thrift.user

struct UserInfo{
    1:i32 id,
    2:string userName,
    3:string password,
    4:string realName,
    5:string mobile,
    6:string email,
    7:string intro,
    8:i32 stars
}

service UserService{

    UserInfo getUserById(1:i32 id);

    UserInfo getTeacherById(1:i32 id);

    UserInfo getUserByUserName(1:string userName);

    void registerUser(1:UserInfo userInfo);

}