package vn.iotstar.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Khuon dang JSON tra ve chung cho tat ca API: status (thanh cong/that bai),
 * message (thong bao), body (du lieu tra ve, co the null).
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Response {
	private Boolean status;
	private String message;
	private Object body;
}
