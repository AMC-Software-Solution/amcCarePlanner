import 'package:dart_json_mapper/dart_json_mapper.dart';

import '../service_user/service_user_model.dart';

@jsonSerializable
class Access {

  @JsonProperty(name: 'id')
  final int id;

  @JsonProperty(name: 'keySafeNumber')
  final String keySafeNumber;

  @JsonProperty(name: 'accessDetails')
  final String accessDetails;

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

 const Access (
     this.id,
        this.keySafeNumber,
        this.accessDetails,
        this.createdDate,
        this.lastUpdatedDate,
        this.clientId,
        this.hasExtraData,
        this.serviceUser,
    );

@override
String toString() {
    return 'Access{'+
    'id: $id,' +
        'keySafeNumber: $keySafeNumber,' +
        'accessDetails: $accessDetails,' +
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
    other is Access &&
    id == other.id ;


@override
int get hashCode =>
    id.hashCode ;
}


