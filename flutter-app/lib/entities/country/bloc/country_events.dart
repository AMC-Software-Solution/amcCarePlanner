part of 'country_bloc.dart';

abstract class CountryEvent extends Equatable {
  const CountryEvent();

  @override
  List<Object> get props => [];

  @override
  bool get stringify => true;
}

class InitCountryList extends CountryEvent {}

class CountryNameChanged extends CountryEvent {
  final String countryName;
  
  const CountryNameChanged({@required this.countryName});
  
  @override
  List<Object> get props => [countryName];
}
class CountryIsoCodeChanged extends CountryEvent {
  final String countryIsoCode;
  
  const CountryIsoCodeChanged({@required this.countryIsoCode});
  
  @override
  List<Object> get props => [countryIsoCode];
}
class CountryFlagUrlChanged extends CountryEvent {
  final String countryFlagUrl;
  
  const CountryFlagUrlChanged({@required this.countryFlagUrl});
  
  @override
  List<Object> get props => [countryFlagUrl];
}
class CountryCallingCodeChanged extends CountryEvent {
  final String countryCallingCode;
  
  const CountryCallingCodeChanged({@required this.countryCallingCode});
  
  @override
  List<Object> get props => [countryCallingCode];
}
class CountryTelDigitLengthChanged extends CountryEvent {
  final int countryTelDigitLength;
  
  const CountryTelDigitLengthChanged({@required this.countryTelDigitLength});
  
  @override
  List<Object> get props => [countryTelDigitLength];
}
class CreatedDateChanged extends CountryEvent {
  final DateTime createdDate;
  
  const CreatedDateChanged({@required this.createdDate});
  
  @override
  List<Object> get props => [createdDate];
}
class LastUpdatedDateChanged extends CountryEvent {
  final DateTime lastUpdatedDate;
  
  const LastUpdatedDateChanged({@required this.lastUpdatedDate});
  
  @override
  List<Object> get props => [lastUpdatedDate];
}
class HasExtraDataChanged extends CountryEvent {
  final bool hasExtraData;
  
  const HasExtraDataChanged({@required this.hasExtraData});
  
  @override
  List<Object> get props => [hasExtraData];
}

class CountryFormSubmitted extends CountryEvent {}

class LoadCountryByIdForEdit extends CountryEvent {
  final int id;

  const LoadCountryByIdForEdit({@required this.id});

  @override
  List<Object> get props => [id];
}

class DeleteCountryById extends CountryEvent {
  final int id;

  const DeleteCountryById({@required this.id});

  @override
  List<Object> get props => [id];
}

class LoadCountryByIdForView extends CountryEvent {
  final int id;

  const LoadCountryByIdForView({@required this.id});

  @override
  List<Object> get props => [id];
}
