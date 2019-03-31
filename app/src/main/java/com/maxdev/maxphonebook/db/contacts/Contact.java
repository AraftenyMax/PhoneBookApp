package com.maxdev.maxphonebook.db.contacts;


import android.os.Parcel;
import android.os.Parcelable;

import com.maxdev.maxphonebook.db.contactgroup.ContactGroup;
import com.maxdev.maxphonebook.db.converters.DateConverter;
import com.maxdev.maxphonebook.utils.DateFormatter;

import java.text.ParseException;
import java.util.Date;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

@Entity(tableName = "Contacts",
        foreignKeys = @ForeignKey(entity = ContactGroup.class, parentColumns = "name", childColumns = "groupId"))
public class Contact implements Parcelable{
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    @TypeConverters({DateConverter.class})
    private Date dateOfBirth;
    private String homeAddress;
    @ColumnInfo(name = "groupId")
    private int groupId;
    private boolean isFavorite;

    public Contact(int id, String firstName, String lastName,
                   String phone, String email, Date dateOfBirth,
                   String homeAddress, int groupId, boolean isFavorite) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.homeAddress = homeAddress;
        this.groupId = groupId;
        this.isFavorite = isFavorite;
    }

    @Ignore
    public Contact(String firstName, String lastName, String email,
                   String phone, String homeAddress, Date dateOfBirth,
                   ContactGroup group, boolean isFavorite) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;
        setDateOfBirth(dateOfBirth);
        this.homeAddress = homeAddress;
        this.groupId = group.getId();
        this.isFavorite = isFavorite;
    }

    @Ignore
    public Contact(String firstName, String lastName, String email,
                   String phone, String dateOfBirth, String homeAddress,
                   ContactGroup group, boolean isFavorite) throws ParseException{
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;
        setDateOfBirth(dateOfBirth);
        this.homeAddress = homeAddress;
        this.groupId = group.getId();
        this.isFavorite = isFavorite;
    }

    protected Contact(Parcel in) {
        id = in.readInt();
        firstName = in.readString();
        lastName = in.readString();
        phone = in.readString();
        email = in.readString();
        homeAddress = in.readString();
        groupId = in.readInt();
        isFavorite = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(firstName);
        dest.writeString(lastName);
        dest.writeString(phone);
        dest.writeString(email);
        dest.writeString(homeAddress);
        dest.writeInt(groupId);
        dest.writeByte((byte) (isFavorite ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Contact> CREATOR = new Creator<Contact>() {
        @Override
        public Contact createFromParcel(Parcel in) {
            return new Contact(in);
        }

        @Override
        public Contact[] newArray(int size) {
            return new Contact[size];
        }
    };

    public int getGroup() {
        return groupId;
    }

    public void setGroup(ContactGroup group) {
        this.groupId = group.getId();
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public Date getDateOfBitrh() {
        return dateOfBirth;
    }

    public void setDateOfBirth(long dateOfBitrh) {
        this.dateOfBirth = DateConverter.fromTimestamp(dateOfBitrh);
    }

    public String getHomeAddress() {
        return homeAddress;
    }

    public void setHomeAddress(String homeAddress) {
        this.homeAddress = homeAddress;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) throws ParseException {
        if (!dateOfBirth.equals(""))
            this.dateOfBirth = DateFormatter.fromString(dateOfBirth);
    }

    public String getFullName() {
        return String.format("%s %s", firstName, lastName);
    }

    public String getFirstChars() {
        return String.format("%c%c", firstName.charAt(0), lastName.charAt(0)).toUpperCase();
    }

}
