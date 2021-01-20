import 'package:dart_json_mapper/dart_json_mapper.dart';


@jsonSerializable
class Country {

  @JsonProperty(name: 'id')
  final int id;

  @JsonProperty(name: 'countryName')
  final String countryName;

  @JsonProperty(name: 'countryIsoCode')
  final String countryIsoCode;

  @JsonProperty(name: 'countryFlagUrl')
  final String countryFlagUrl;

  @JsonProperty(name: 'countryCallingCode')
  final String countryCallingCode;

  @JsonProperty(name: 'countryTelDigitLength')
  final int countryTelDigitLength;

  @JsonProperty(name: 'createdDate', converterParams: {'format': 'yyyy-MM-dd\'T\'HH:mm:ss\'Z\''})
  final DateTime createdDate;

  @JsonProperty(name: 'lastUpdatedDate', converterParams: {'format': 'yyyy-MM-dd\'T\'HH:mm:ss\'Z\''})
  final DateTime lastUpdatedDate;

  @JsonProperty(name: 'hasExtraData')
  final bool hasExtraData;
        
 const Country (
     this.id,
        this.countryName,
        this.countryIsoCode,
        this.countryFlagUrl,
        this.countryCallingCode,
        this.countryTelDigitLength,
        this.createdDate,
        this.lastUpdatedDate,
        this.hasExtraData,
    );

@override
String toString() {
    return 'Country{'+
    'id: $id,' +
        'countryName: $countryName,' +
        'countryIsoCode: $countryIsoCode,' +
        'countryFlagUrl: $countryFlagUrl,' +
        'countryCallingCode: $countryCallingCode,' +
        'countryTelDigitLength: $countryTelDigitLength,' +
        'createdDate: $createdDate,' +
        'lastUpdatedDate: $lastUpdatedDate,' +
        'hasExtraData: $hasExtraData,' +
    '}';
   }

@override
bool operator == (Object other) => 
    identical(this, other) ||
    other is Country &&
    id == other.id ;


@override
int get hashCode => 
    id.hashCode ;
}


