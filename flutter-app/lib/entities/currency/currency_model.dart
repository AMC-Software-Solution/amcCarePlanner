import 'package:dart_json_mapper/dart_json_mapper.dart';


@jsonSerializable
class Currency {

  @JsonProperty(name: 'id')
  final int id;

  @JsonProperty(name: 'currency')
  final String currency;

  @JsonProperty(name: 'currencyIsoCode')
  final String currencyIsoCode;

  @JsonProperty(name: 'currencySymbol')
  final String currencySymbol;

  @JsonProperty(name: 'currencyLogoUrl')
  final String currencyLogoUrl;

  @JsonProperty(name: 'hasExtraData')
  final bool hasExtraData;
        
 const Currency (
     this.id,
        this.currency,
        this.currencyIsoCode,
        this.currencySymbol,
        this.currencyLogoUrl,
        this.hasExtraData,
    );

@override
String toString() {
    return 'Currency{'+
    'id: $id,' +
        'currency: $currency,' +
        'currencyIsoCode: $currencyIsoCode,' +
        'currencySymbol: $currencySymbol,' +
        'currencyLogoUrl: $currencyLogoUrl,' +
        'hasExtraData: $hasExtraData,' +
    '}';
   }

@override
bool operator == (Object other) => 
    identical(this, other) ||
    other is Currency &&
    id == other.id ;


@override
int get hashCode => 
    id.hashCode ;
}


