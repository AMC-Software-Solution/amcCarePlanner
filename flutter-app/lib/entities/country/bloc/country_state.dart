part of 'country_bloc.dart';

enum CountryStatusUI {init, loading, error, done}
enum CountryDeleteStatus {ok, ko, none}

class CountryState extends Equatable {
  final List<Country> countries;
  final Country loadedCountry;
  final bool editMode;
  final CountryDeleteStatus deleteStatus;
  final CountryStatusUI countryStatusUI;

  final FormzStatus formStatus;
  final String generalNotificationKey;

  final CountryNameInput countryName;
  final CountryIsoCodeInput countryIsoCode;
  final CountryFlagUrlInput countryFlagUrl;
  final CountryCallingCodeInput countryCallingCode;
  final CountryTelDigitLengthInput countryTelDigitLength;
  final CreatedDateInput createdDate;
  final LastUpdatedDateInput lastUpdatedDate;
  final HasExtraDataInput hasExtraData;

  
  CountryState(
CreatedDateInput createdDate,LastUpdatedDateInput lastUpdatedDate,{
    this.countries = const [],
    this.countryStatusUI = CountryStatusUI.init,
    this.loadedCountry = const Country(0,'','','','',0,null,null,false,),
    this.editMode = false,
    this.formStatus = FormzStatus.pure,
    this.generalNotificationKey = '',
    this.deleteStatus = CountryDeleteStatus.none,
    this.countryName = const CountryNameInput.pure(),
    this.countryIsoCode = const CountryIsoCodeInput.pure(),
    this.countryFlagUrl = const CountryFlagUrlInput.pure(),
    this.countryCallingCode = const CountryCallingCodeInput.pure(),
    this.countryTelDigitLength = const CountryTelDigitLengthInput.pure(),
    this.hasExtraData = const HasExtraDataInput.pure(),
  }):this.createdDate = createdDate ?? CreatedDateInput.pure(),
this.lastUpdatedDate = lastUpdatedDate ?? LastUpdatedDateInput.pure()
;

  CountryState copyWith({
    List<Country> countries,
    CountryStatusUI countryStatusUI,
    bool editMode,
    CountryDeleteStatus deleteStatus,
    Country loadedCountry,
    FormzStatus formStatus,
    String generalNotificationKey,
    CountryNameInput countryName,
    CountryIsoCodeInput countryIsoCode,
    CountryFlagUrlInput countryFlagUrl,
    CountryCallingCodeInput countryCallingCode,
    CountryTelDigitLengthInput countryTelDigitLength,
    CreatedDateInput createdDate,
    LastUpdatedDateInput lastUpdatedDate,
    HasExtraDataInput hasExtraData,
  }) {
    return CountryState(
        createdDate,
        lastUpdatedDate,
      countries: countries ?? this.countries,
      countryStatusUI: countryStatusUI ?? this.countryStatusUI,
      loadedCountry: loadedCountry ?? this.loadedCountry,
      editMode: editMode ?? this.editMode,
      formStatus: formStatus ?? this.formStatus,
      generalNotificationKey: generalNotificationKey ?? this.generalNotificationKey,
      deleteStatus: deleteStatus ?? this.deleteStatus,
      countryName: countryName ?? this.countryName,
      countryIsoCode: countryIsoCode ?? this.countryIsoCode,
      countryFlagUrl: countryFlagUrl ?? this.countryFlagUrl,
      countryCallingCode: countryCallingCode ?? this.countryCallingCode,
      countryTelDigitLength: countryTelDigitLength ?? this.countryTelDigitLength,
      hasExtraData: hasExtraData ?? this.hasExtraData,
    );
  }

  @override
  List<Object> get props => [countries, countryStatusUI,
     loadedCountry, editMode, deleteStatus, formStatus, generalNotificationKey, 
countryName,countryIsoCode,countryFlagUrl,countryCallingCode,countryTelDigitLength,createdDate,lastUpdatedDate,hasExtraData,];

  @override
  bool get stringify => true;
}
