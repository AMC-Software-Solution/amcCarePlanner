import 'package:dart_json_mapper/dart_json_mapper.dart';

import '../service_user/service_user_model.dart';

@jsonSerializable
class ServiceUserContact {

  @JsonProperty(name: 'id')
  final int id;

  @JsonProperty(name: 'address')
  final String address;

  @JsonProperty(name: 'cityOrTown')
  final String cityOrTown;

  @JsonProperty(name: 'county')
  final String county;

  @JsonProperty(name: 'postCode')
  final String postCode;

  @JsonProperty(name: 'telephone')
  final String telephone;

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

 const ServiceUserContact (
     this.id,
        this.address,
        this.cityOrTown,
        this.county,
        this.postCode,
        this.telephone,
        this.createdDate,
        this.lastUpdatedDate,
        this.clientId,
        this.hasExtraData,
        this.serviceUser,
    );

@override
String toString() {
    return 'ServiceUserContact{'+
    'id: $id,' +
        'address: $address,' +
        'cityOrTown: $cityOrTown,' +
        'county: $county,' +
        'postCode: $postCode,' +
        'telephone: $telephone,' +
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
    other is ServiceUserContact &&
    id == other.id ;


@override
int get hashCode =>
    id.hashCode ;
}


