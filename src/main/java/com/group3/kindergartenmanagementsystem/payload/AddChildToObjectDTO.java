package com.group3.kindergartenmanagementsystem.payload;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class AddChildToObjectDTO {
    List<Integer> childIds;
    Integer objectId;
}
