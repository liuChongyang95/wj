package JavaBean;

public class Home {
    private String roomId;
    private String roomAddress;
    private String roomType;
    private String roomUser;
    private String roomPub;
    private String roomCost;


    public Home(String roomAddress,String roomType,String roomUser,String roomPub,String roomCost){
        this.roomAddress=roomAddress;
        this.roomType=roomType;
        this.roomUser=roomUser;
        this.roomPub=roomPub;
        this.roomCost=roomCost;
    }


    public String getRoomCost() {
        return roomCost;
    }

    public void setRoomCost(String roomCost) {
        this.roomCost = roomCost;
    }

    public String getRoomPub() {
        return roomPub;
    }

    public void setRoomPub(String roomPub) {
        this.roomPub = roomPub;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getRoomAddress() {
        return roomAddress;
    }

    public void setRoomAddress(String roomAddress) {
        this.roomAddress = roomAddress;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public String getRoomUser() {
        return roomUser;
    }

    public void setRoomUser(String roomUser) {
        this.roomUser = roomUser;
    }
}
