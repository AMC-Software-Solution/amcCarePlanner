import 'package:dart_json_mapper/dart_json_mapper.dart';

import '../service_user/service_user_model.dart';

@jsonSerializable
class MedicalContact {

  @JsonProperty(name: 'id')
  final int id;

  @JsonProperty(name: 'doctorName')
  final String doctorName;

  @JsonProperty(name: 'doctorSurgery')
  final String doctorSurgery;

  @JsonProperty(name: 'doctorAddress')
  final String doctorAddress;

  @JsonProperty(name: 'doctorPhone')
  final String doctorPhone;

  @JsonProperty(name: 'lastVisitedDoctor', converterParams: {'format': 'yyyy-MM-dd\'T\'HH:mm:ss\'Z\''})
  final DateTime lastVisitedDoctor;

  @JsonProperty(name: 'districtNurseName')
  final String districtNurseName;

  @JsonProperty(name: 'districtNursePhone')
  final String districtNursePhone;

  @JsonProperty(name: 'careManagerName')
  final String careManagerName;

  @JsonProperty(name: 'careManagerPhone')
  final String careManagerPhone;

  @JsonProperty(name: 'createdDate', converterParams: {'format': 'yyyy-MM-dd\'T\'HH:mm:ss\'Z\''})
  final DateTime createdDate;

  @JsonProperty(name: 'lastUpdatedDate', converterParams: {'format': 'yyyy-MM-dd\'T\'HH:mm:ss\'Z\''})
  final DateTime lastUpdatedDate;

  @JsonProperty(name: 'clientId')
  final int clientId;

  @JsonProperty(name: 'hasExtraData')
  final bool hasExtraData;

  @JsonProperty(name: 'serviceUser')
  final ServiceUser serviceUser;

 const MedicalContact (
     this.id,
        this.doctorName,
        this.doctorSurgery,
        this.doctorAddress,
        this.doctorPhone,
        this.lastVisitedDoctor,
        this.districtNurseName,
        this.districtNursePhone,
        this.careManagerName,
        this.careManagerPhone,
        this.createdDate,
        this.lastUpdatedDate,
        this.clientId,
        this.hasExtraData,
        this.serviceUser,
    );

@override
String toString() {
    return 'MedicalContact{'+
    'id: $id,' +
        'doctorName: $doctorName,' +
        'doctorSurgery: $doctorSurgery,' +
        'doctorAddress: $doctorAddress,' +
        'doctorPhone: $doctorPhone,' +
        'lastVisitedDoctor: $lastVisitedDoctor,' +
        'districtNurseName: $districtNurseName,' +
        'districtNursePhone: $districtNursePhone,' +
        'careManagerName: $careManagerName,' +
        'careManagerPhone: $careManagerPhone,' +
        'createdDate: $createdDate,' +
        'lastUpdatedDate: $lastUpdatedDate,' +
        'clientId: $clientId,' +
        'hasExtraData: $hasExtraData,' +
        'serviceUser: $serviceUser,' +
    '}';
   }

@override
bool operator == (Object other) =>
    identical(this, other) ||
    other is MedicalContact &&
    id == other.id ;


@override
int get hashCode =>
    id.hashCode ;
}


