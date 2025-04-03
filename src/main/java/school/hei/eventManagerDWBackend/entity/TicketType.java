package school.hei.eventManagerDWBackend.entity;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class TicketType {
  private int id;
  private int eventId;
  private String name;
  private Long price;
  private int available_quantity;

  public TicketType(int id, int eventId, String name, Long price, int availableQuantity) {
    this.id = id;
    this.eventId = eventId;
    this.name = name;
    this.price = price;
    this.available_quantity = availableQuantity;
  }


  public boolean isAvailable() {
    return available_quantity > 0;
  }

  public void decreaseQuantity(int amount) {
    if (amount > available_quantity) {
      throw new IllegalArgumentException("Not enough available tickets");
    }
    this.available_quantity -= amount;
  }

  public void increaseQuantity(int amount) {
    this.available_quantity += amount;
  }
}
