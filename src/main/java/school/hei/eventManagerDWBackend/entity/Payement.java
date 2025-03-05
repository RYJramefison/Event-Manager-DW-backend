package school.hei.eventManagerDWBackend.entity;

import java.time.LocalDateTime;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@EqualsAndHashCode
public class Payement {
  private int id;
  private int reservationId;
  private Long Amount;
  private PaymentMethod paymentMethod;
  private StatusPayement statusPayment;
  private LocalDateTime PayementDate;
}
