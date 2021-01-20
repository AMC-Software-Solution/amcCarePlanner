import 'package:dart_json_mapper/dart_json_mapper.dart';

import 'package:amcCarePlanner/shared/models/user.dart';
import '../branch/branch_model.dart';
import '../employee/employee_model.dart';
import '../employee/employee_model.dart';

@jsonSerializable
class ServiceUser {

  @JsonProperty(name: 'id')
  final int id;

  @JsonProperty(name: 'titlle', enumValues: Titlle.values)
  final Titlle titlle;

  @JsonProperty(name: 'firstName')
  final String firstName;

  @JsonProperty(name: 'middleName')
  final String middleName;

  @JsonProperty(name: 'lastName')
  final String lastName;

  @JsonProperty(name: 'preferredName')
  final String preferredName;

  @JsonProperty(name: 'email')
  final String email;

  @JsonProperty(name: 'serviceUserCode')
  final String serviceUserCode;

  @JsonProperty(name: 'dateOfBirth', converterParams: {'format': 'yyyy-MM-dd\'T\'HH:mm:ss\'Z\''})
  final DateTime dateOfBirth;

  @JsonProperty(name: 'lastVisitDate', converterParams: {'format': 'yyyy-MM-dd\'T\'HH:mm:ss\'Z\''})
  final DateTime lastVisitDate;

  @JsonProperty(name: 'startDate', converterParams: {'format': 'yyyy-MM-dd\'T\'HH:mm:ss\'Z\''})
  final DateTime startDate;

  @JsonProperty(name: 'supportType', enumValues: SupportType.values)
  final SupportType supportType;

  @JsonProperty(name: 'serviceUserCategory', enumValues: ServiceUserCategory.values)
  final ServiceUserCategory serviceUserCategory;

  @JsonProperty(name: 'vulnerability', enumValues: Vulnerability.values)
  final Vulnerability vulnerability;

  @JsonProperty(name: 'servicePriority', enumValues: ServicePriority.values)
  final ServicePriority servicePriority;

  @JsonProperty(name: 'source', enumValues: Source.values)
  final Source source;

  @JsonProperty(name: 'status', enumValues: ServiceUserStatus.values)
  final ServiceUserStatus status;

  @JsonProperty(name: 'firstLanguage')
  final String firstLanguage;

  @JsonProperty(name: 'interpreterRequired')
  final bool interpreterRequired;

  @JsonProperty(name: 'activatedDate', converterParams: {'format': 'yyyy-MM-dd\'T\'HH:mm:ss\'Z\''})
  final DateTime activatedDate;

  @JsonProperty(name: 'profilePhotoUrl')
  final String profilePhotoUrl;

  @JsonProperty(name: 'lastRecordedHeight')
  final String lastRecordedHeight;

  @JsonProperty(name: 'lastRecordedWeight')
  final String lastRecordedWeight;

  @JsonProperty(name: 'hasMedicalCondition')
  final bool hasMedicalCondition;

  @JsonProperty(name: 'medicalConditionSummary')
  final String medicalConditionSummary;

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

  @JsonProperty(name: 'branch')
  final Branch branch;

  @JsonProperty(name: 'registeredBy')
  final Employee registeredBy;

  @JsonProperty(name: 'activatedBy')
  final Employee activatedBy;
        
 const ServiceUser (
     this.id,
        this.titlle,
        this.firstName,
        this.middleName,
        this.lastName,
        this.preferredName,
        this.email,
        this.serviceUserCode,
        this.dateOfBirth,
        this.lastVisitDate,
        this.startDate,
        this.supportType,
        this.serviceUserCategory,
        this.vulnerability,
        this.servicePriority,
        this.source,
        this.status,
        this.firstLanguage,
        this.interpreterRequired,
        this.activatedDate,
        this.profilePhotoUrl,
        this.lastRecordedHeight,
        this.lastRecordedWeight,
        this.hasMedicalCondition,
        this.medicalConditionSummary,
        this.createdDate,
        this.lastUpdatedDate,
        this.clientId,
        this.hasExtraData,
        this.user,
        this.branch,
        this.registeredBy,
        this.activatedBy,
    );

@override
String toString() {
    return 'ServiceUser{'+
    'id: $id,' +
        'titlle: $titlle,' +
        'firstName: $firstName,' +
        'middleName: $middleName,' +
        'lastName: $lastName,' +
        'preferredName: $preferredName,' +
        'email: $email,' +
        'serviceUserCode: $serviceUserCode,' +
        'dateOfBirth: $dateOfBirth,' +
        'lastVisitDate: $lastVisitDate,' +
        'startDate: $startDate,' +
        'supportType: $supportType,' +
        'serviceUserCategory: $serviceUserCategory,' +
        'vulnerability: $vulnerability,' +
        'servicePriority: $servicePriority,' +
        'source: $source,' +
        'status: $status,' +
        'firstLanguage: $firstLanguage,' +
        'interpreterRequired: $interpreterRequired,' +
        'activatedDate: $activatedDate,' +
        'profilePhotoUrl: $profilePhotoUrl,' +
        'lastRecordedHeight: $lastRecordedHeight,' +
        'lastRecordedWeight: $lastRecordedWeight,' +
        'hasMedicalCondition: $hasMedicalCondition,' +
        'medicalConditionSummary: $medicalConditionSummary,' +
        'createdDate: $createdDate,' +
        'lastUpdatedDate: $lastUpdatedDate,' +
        'clientId: $clientId,' +
        'hasExtraData: $hasExtraData,' +
        'user: $user,' +
        'branch: $branch,' +
        'registeredBy: $registeredBy,' +
        'activatedBy: $activatedBy,' +
    '}';
   }

@override
bool operator == (Object other) => 
    identical(this, other) ||
    other is ServiceUser &&
    id == other.id ;


@override
int get hashCode => 
    id.hashCode ;
}


@jsonSerializable
@Json(enumValues: Titlle.values)
enum Titlle {
    MR ,
    MRS ,
    MS ,
    MISS ,
    OTHER 
}@jsonSerializable
@Json(enumValues: SupportType.values)
enum SupportType {
    COMPLEX_CARE_LIVE_IN ,
    DOMICILIARY_CARE ,
    EXTRA_CARE ,
    PRIVATE ,
    REABLEMENT 
}@jsonSerializable
@Json(enumValues: ServiceUserCategory.values)
enum ServiceUserCategory {
    HIV_AIDS ,
    LEARNING_DISABILITIES ,
    MENTAL_HEALTH ,
    OLDER_PEOPLE ,
    PHYSICAL_SENSORY_IMPAIRMENT ,
    OTHER 
}@jsonSerializable
@Json(enumValues: Vulnerability.values)
enum Vulnerability {
    HIV_AIDS ,
    LEARNING_DISABILITIES ,
    MENTAL_HEALTH ,
    OLDER_PEOPLE ,
    PHYSICAL_IMPAIRMENT ,
    SENSORY_IMPAIRMENT 
}@jsonSerializable
@Json(enumValues: ServicePriority.values)
enum ServicePriority {
    HIGH ,
    LOW ,
    MEDIUM 
}@jsonSerializable
@Json(enumValues: Source.values)
enum Source {
    PRIVATE_SERVICE_USER ,
    SOCIAL_SERVICES_REFERRAL ,
    REABLEMENT_REFERRAL ,
    UNKNOWN 
}@jsonSerializable
@Json(enumValues: ServiceUserStatus.values)
enum ServiceUserStatus {
    ACTIVE ,
    DEACTIVE 
}