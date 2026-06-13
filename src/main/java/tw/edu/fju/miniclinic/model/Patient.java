package tw.edu.fju.miniclinic.model;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.sql.Types;
import java.time.LocalDate;
import org.hibernate.annotations.JdbcTypeCode;

@Entity
@Table(name = "patient")
public class Patient {
    // 病歷號 (chart_no) 作為主鍵
    @Id
    @Column(name = "chart_no", length = 20)
    private String chartNo;

    @Column(name = "name", length = 50, nullable = false)
    private String name;

    @Column(name = "gender", length = 10)
    private String gender;

    @JdbcTypeCode(Types.VARCHAR)
    @Convert(converter = LocalDateConverter.class)
    @Column(name = "birth_date", columnDefinition = "TEXT")
    private LocalDate birthDate;

    @Column(name = "phone", length = 20)
    private String phone;

    public Patient() {
    }

    public Patient(String chartNo, String name, String gender, LocalDate birthDate, String phone) {
        this.chartNo = chartNo; // 病歷號
        this.name = name; // 病人姓名
        this.gender = gender; // 病人性別
        this.birthDate = birthDate; // 病人出生日期
        this.phone = phone; // 病人聯絡電話
    }
    // Getter 和 Setter 方法
    public String getChartNo() {
        return chartNo;
    }
    public void setChartNo(String chartNo) {
        this.chartNo = chartNo;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }
    public LocalDate getBirthDate() {
        return birthDate;
    }
    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
}