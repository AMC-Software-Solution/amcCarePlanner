import 'package:dart_json_mapper/dart_json_mapper.dart';

import '../service_user/service_user_model.dart';
import '../employee/employee_model.dart';

@jsonSerializable
class Communication {

  @JsonProperty(name: 'id')
  final int id;

  @JsonProperty(name: 'communicationType', enumValues: CommunicationType.values)
  final CommunicationType communicationType;

  @JsonProperty(name: 'note')
  final String note;

  @JsonProperty(name: 'communicationDate', converterParams: {'format': 'yyyy-MM-dd\'T\'HH:mm:ss\'Z\''})
  final DateTime communicationDate;

  @JsonProperty(name: 'attachmentUrl')
  final String attachmentUrl;

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

  @JsonProperty(name: 'communicatedBy')
  final Employee communicatedBy;

 const Communication (
     this.id,
        this.communicationType,
        this.note,
        this.communicationDate,
        this.attachmentUrl,
        this.createdDate,
        this.lastUpdatedDate,
        this.clientId,
        this.hasExtraData,
        this.serviceUser,
        this.communicatedBy,
    );

@override
String toString() {
    return 'Communication{'+
    'id: $id,' +
        'communicationType: $communicationType,' +
        'note: $note,' +
        'communicationDate: $communicationDate,' +
        'attachmentUrl: $attachmentUrl,' +
        'createdDate: $createdDate,' +
        'lastUpdatedDate: $lastUpdatedDate,' +
        'clientId: $clientId,' +
        'hasExtraData: $hasExtraData,' +
        'serviceUser: $serviceUser,' +
        'communicatedBy: $communicatedBy,' +
    '}';
   }

@override
bool operator == (Object other) =>
    identical(this, other) ||
    other is Communication &&
    id == other.id ;


@override
int get hashCode =>
    id.hashCode ;
}


@jsonSerializable
@Json(enumValues: CommunicationType.values)
enum CommunicationType {
    EMAIL ,
    SMS ,
    TELEPHONE ,
    MAIL ,
    IN_PERSON ,
    OTHER
}
