package com.example.grade_spring_batch.model;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tests")
public class Test {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "account_id")
    private Student studentAccount;
    @Transient
    private Long accountId;

    private Double test1;
    private Double test2;
    private Double test3;
    private Double test4;

}
