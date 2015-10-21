PCCRovers Tracker Api
=============================

#Target Endpoints
##User
- /user/{user_id} `User`
- /user/{user_id}/group `array[Group]`
- /user/{user_id}/group/{group_id} `Group`
- /user/{user_id}/sections `array[Section]`
- /user/{user_id}/sections/{section_id} `Section`
- /user/{user_id}/attendance `array[AttendanceRecord]`

##Group
- /group/{group_id} `Group`
- /group/{group_id}/sections `array[Section]`
- /group/{group_id}/sections/{section_id} `Section`
- /group/users `array[User]`
- /group/users/{user_id} `User`
- /group/leaders `array[User]`
- /group/leaders/{user_id} `array[User]`
- /group/participants `array[User]`
- /group/participants/{user_id} `array[User]`

##Section
- /section/{section_id} `Section`
- /section/{section_id}/users `array[User]`
- /section/{section_id}/users/{user_id} `User`
- /section/{section_id}/leaders `array[User]`
- /section/{section_id}/leaders/{user_id} `User`
- /section/{section_id}/participants `array[User]`
- /section/{section_id}/participants/{user_id} `User`
- /section/{section_id}/patrols `array[Patrol]`
- /section/{section_id}/patrols/{patrol_id} `Patrol`
- /section/{section_id}/inventory `array[Item]`
- /section/{section_id}/inventory/{item_id} `Item`

##Attendance
- /attendance/user/{user_id} `AttendanceOverview`
- /attendance/user/{user_id}/records `array[AttendanceRecord]`
- /attendance/user/{user_id}/records/{attendance_record_id} `AttendanceRecord`
- /attendance/group/{group_id} `AttendanceOverview`
- /attendance/group/{group_id}/records `array[AttendanceRecord]`
- /attendance/group/{group_id}/records/{attendance_record_id} `AttendanceRecord`
- /attendance/section/{section_id} `AttendanceOverview`
- /attendance/section/{section_id}/records `array[AttendanceRecord]`
- /attendance/section/{section_id}/records/{attendance_record_id} `AttendanceRecord`

#Data Models
- User
    - `public long id;`
    - `public long google_id;`
    - `public long name;`
- Group
    - `public long id;`
    - `public String name;`
- Section
    - `public long id;`
    - `public String name;`
- Patrol
    - `public long id;`
    - `public String name;`
- Item
    - `public long id;`
    - `public String genus;`
    - `public String brand;`
    - `public String product;`
    - `public String model;`
    - `public String serial_number;`
    - `public int number`
    - `public long borrower_user_id;`
- AttendanceRecord
    - `public long id;`
    - `public long user_id;`
    - `public long date;`
    - `public String presence;`
- AttendanceOverview
    - To Be Determined
