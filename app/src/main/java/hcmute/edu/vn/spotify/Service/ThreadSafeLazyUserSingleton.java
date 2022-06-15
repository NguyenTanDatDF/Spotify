package hcmute.edu.vn.spotify.Service;

import hcmute.edu.vn.spotify.Model.User;

public class ThreadSafeLazyUserSingleton {
    public User user;

    //Biến volatile trong Java có tác dụng thông báo sự thay
    // đổi giá trị của biến tới các thread khác nhau nếu biến này đang được sử dụng trong nhiều thread.
    private static volatile ThreadSafeLazyUserSingleton instance;

    private ThreadSafeLazyUserSingleton(User user) {
        this.user = user;
    }

//    Đồng bộ trong java (Synchronization in java) là khả năng kiểm soát truy cập của nhiều luồng
//    đến bất kỳ nguồn tài nguyên chia sẻ. Để tránh sự can thiệp của luồng khác
    public static synchronized ThreadSafeLazyUserSingleton getInstance(User user) {
        // Check xem nó chưa được khởi tạo thì khởi tạo (Initialization)
        if (instance == null) {
            instance = new ThreadSafeLazyUserSingleton(user);
        }
        return instance;
    }
}
