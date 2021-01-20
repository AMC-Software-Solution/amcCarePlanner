import 'package:dart_json_mapper/dart_json_mapper.dart';

import 'package:amcCarePlanner/shared/models/user.dart';
import '../employee_designation/employee_designation_model.dart';
import '../country/country_model.dart';
import '../branch/branch_model.dart';

@jsonSerializable
class Employee {

  @JsonProperty(name: 'id')
  final int id;

  @JsonProperty(name: 'tittle', enumValues: Tittle.values)
  final Tittle tittle;

  @JsonProperty(name: 'firstName')
  final String firstName;

  @JsonProperty(name: 'middleInitial')
  final String middleInitial;

  @JsonProperty(name: 'lastName')
  final String lastName;

  @JsonProperty(name: 'preferredName')
  final String preferredName;

  @JsonProperty(name: 'gendder', enumValues: Gendder.values)
  final Gendder gendder;

  @JsonProperty(name: 'employeeCode')
  final String employeeCode;

  @JsonProperty(name: 'phone')
  final String phone;

  @JsonProperty(name: 'email')
  final String email;

  @JsonProperty(name: 'nationalInsuranceNumber')
  final String nationalInsuranceNumber;

  @JsonProperty(name: 'employeeContractType', enumValues: EmployeeContractType.values)
  final EmployeeContractType employeeContractType;

  @JsonProperty(name: 'pinCode')
  final int pinCode;

  @JsonProperty(name: 'employeeTransportMode', enumValues: EmployeeTravelMode.values)
  final EmployeeTravelMode employeeTransportMode;

  @JsonProperty(name: 'address')
  final String address;

  @JsonProperty(name: 'county')
  final String county;

  @JsonProperty(name: 'postCode')
  final String postCode;

  @JsonProperty(name: 'dateOfBirth', converterParams: {'format': 'yyyy-MM-dd\'T\'HH:mm:ss\'Z\''})
  final DateTime dateOfBirth;

  @JsonProperty(name: 'photoUrl')
  final String photoUrl;

  @JsonProperty(name: 'status', enumValues: EmployeeStatus.values)
  final EmployeeStatus status;

  @JsonProperty(name: 'employeeBio')
  final String employeeBio;

  @JsonProperty(name: 'acruedHolidayHours')
  final int acruedHolidayHours;

  @JsonProperty(name: 'createdDate', converterParams: {'format': 'yyyy-MM-dd\'T\'HH:mm:ss\'Z\''})
  final DateTime createdDate;

  @JsonProperty(name: 'lastUpdatedDate', converterParams: {'format': 'yyyy-MM-dd\'T\'HH:mm:ss\'Z\''})
  final DateTime lastUpdatedDate;

  @JsonProperty(name: 'clientId')
  final int clientId;

  @JsonProperty(name: 'hasExtraData')
  final bool hasExtraData;

  @JsonProperty(name: 'user')
  final User user;

  @JsonProperty(name: 'designation')
  final EmployeeDesignation designation;

  @JsonProperty(name: 'nationality')
  final Country nationality;

  @JsonProperty(name: 'branch')
  final Branch branch;

 const Employee (
     this.id,
        this.tittle,
        this.firstName,
        this.middleInitial,
        this.lastName,
        this.preferredName,
        this.gendder,
        this.employeeCode,
        this.phone,
        this.email,
        this.nationalInsuranceNumber,
        this.employeeContractType,
        this.pinCode,
        this.employeeTransportMode,
        this.address,
        this.county,
        this.postCode,
        this.dateOfBirth,
        this.photoUrl,
        this.status,
        this.employeeBio,
        this.acruedHolidayHours,
        this.createdDate,
        this.lastUpdatedDate,
        this.clientId,
        this.hasExtraData,
        this.user,
        this.designation,
        this.nationality,
        this.branch,
    );

@override
String toString() {
    return 'Employee{'+
    'id: $id,' +
        'tittle: $tittle,' +
        'firstName: $firstName,' +
        'middleInitial: $middleInitial,' +
        'lastName: $lastName,' +
        'preferredName: $preferredName,' +
        'gendder: $gendder,' +
        'employeeCode: $employeeCode,' +
        'phone: $phone,' +
        'email: $email,' +
        'nationalInsuranceNumber: $nationalInsuranceNumber,' +
        'employeeContractType: $employeeContractType,' +
        'pinCode: $pinCode,' +
        'employeeTransportMode: $employeeTransportMode,' +
        'address: $address,' +
        'county: $county,' +
        'postCode: $postCode,' +
        'dateOfBirth: $dateOfBirth,' +
        'photoUrl: $photoUrl,' +
        'status: $status,' +
        'employeeBio: $employeeBio,' +
        'acruedHolidayHours: $acruedHolidayHours,' +
        'createdDate: $createdDate,' +
        'lastUpdatedDate: $lastUpdatedDate,' +
        'clientId: $clientId,' +
        'hasExtraData: $hasExtraData,' +
        'user: $user,' +
        'designation: $designation,' +
        'nationality: $nationality,' +
        'branch: $branch,' +
    '}';
   }

@override
bool operator == (Object other) =>
    identical(this, other) ||
    other is Employee &&
    id == other.id ;


@override
int get hashCode =>
    id.hashCode ;
}


@jsonSerializable
@Json(enumValues: Tittle.values)
enum Tittle {
    MR ,
    MRS ,
    MS ,
    MISS ,
    OTHER
}@jsonSerializable
@Json(enumValues: Gendder.values)
enum Gendder {
    MALE ,
    FEMALE ,
    OTHER
}@jsonSerializable
@Json(enumValues: EmployeeContractType.values)
enum EmployeeContractType {
    ZERO_HOURS_CONTRACT ,
    SALARIED_STAFF
}@jsonSerializable
@Json(enumValues: EmployeeTravelMode.values)
enum EmployeeTravelMode {
    CAR ,
    BUS ,
    TRAIN ,
    PLANE ,
    SHIP ,
    WALK ,
    OTHER
}@jsonSerializable
@Json(enumValues: EmployeeStatus.values)
enum EmployeeStatus {
    ACTIVE ,
    DEACTIVE
}
