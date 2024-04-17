package com.example._memo_noted_takingapp.Model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NoteType {
   private Integer notedId;
   private String type_name;
   private Integer noteTypeId;
}
