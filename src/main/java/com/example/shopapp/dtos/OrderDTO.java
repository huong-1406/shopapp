package com.example.shopapp.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@Builder//Nó là công cụ của thư viện Lombok, chỉ hỗ trợ cú pháp viết code Java nhanh hơn.
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class OrderDTO {
    //tròn java khi khai báo private string thì tên ví dụ fullname trong database thì chữ fullName phải viếtb thế này nên phải dùng Json để đúng định dạng trong database
//Hầu hết các trường hợp bạn phải dùng đến @JsonProperty là để xử lý các biến liên quan đến Database có đuôi _id (như user_id, category_id, order_id...). Vì Database thích dùng gạch dưới (_), còn Java lại dùng chữ viết hoa (Id), nên chúng nó bị vênh nhau và cần @JsonProperty làm cầu nối để dễ sửa và bảo trì.Tuy nhiên, bạn mở rộng tư duy ra thêm một chút nữa nhé: Không chỉ mỗi đuôi _id đâu, mà bất kỳ biến nào có từ ghép (gồm 2 từ trở lên) trong Database cũng sẽ cần dùng.Ví dụ thực tế:Cột database là create_at (ngày tạo) $\rightarrow$ Java là createdAt $\rightarrow$ Cần dùng @JsonProperty("create_at")Cột database là total_price (tổng tiền) $\rightarrow$ Java là totalPrice $\rightarrow$ Cần dùng @JsonProperty("total_price")Cột database là is_deleted (đã xóa chưa) $\rightarrow$ Java là isDeleted $\rightarrow$ Cần dùng @JsonProperty("is_deleted")
//@JsonProperty("user_id") là bộ dịch giúp code java giữ nguyen chuẩn @Json giúp cô lập thay đổi nếu sau này API thay đổi thì chỉ cần sưuar ở Json thôi mf không cần sửa ở public và private
    //mặc dù ở private long user_id vẫn được nhưng khi sửa thì nó rất phức tạp bởi
    @JsonProperty("user_id")

    //cáiid INT PRIMARY KEY AUTO_INCREMENT,vàorder_date DATETIME DEFAULT CURRENT_TIMESTAMP,AUTO_INCREMENT, CURRENT_TIMESTAMP nó là mặc định nên tự sinh
    //vậy nên không cần truyêền từ client lên
    //biến usedId phải btws buộc từ 1 trở lên nếu lỗi nó  in ra thông báo này User id must be > 0
    @Min(value = 1,message = "User id must be > 0")
    private Long userId;

    @JsonProperty("fullname")
    private String fullName;


    private String email;


    @JsonProperty("phone_number")
    @NotBlank(message = "phone number is required")//vòng bảo vệ danh cho chuỗi không được để trống nếu để trống nó in ra thông báo nàyphone number is required
    @Size(min=5,message = "phone number must be at least 5 characters")
    private String phoneNumber;

    private  String address;

    private String note;


    @JsonProperty("total_money")
    @Min(value = 0,message = "Total money must be  >=0")//tổng số tièn phải dương đương nhiên tiền không thể âm nếu
    private Float totalMoney;


    @JsonProperty("shipping_method")
    private String shippingMethod;

    @JsonProperty("shipping_address")
    private String shippingAddress;

    @JsonProperty("payment_method")
    private String paymentMethod;



}
