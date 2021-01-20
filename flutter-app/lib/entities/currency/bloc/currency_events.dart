part of 'currency_bloc.dart';

abstract class CurrencyEvent extends Equatable {
  const CurrencyEvent();

  @override
  List<Object> get props => [];

  @override
  bool get stringify => true;
}

class InitCurrencyList extends CurrencyEvent {}

class CurrencyChanged extends CurrencyEvent {
  final String currency;
  
  const CurrencyChanged({@required this.currency});
  
  @override
  List<Object> get props => [currency];
}
class CurrencyIsoCodeChanged extends CurrencyEvent {
  final String currencyIsoCode;
  
  const CurrencyIsoCodeChanged({@required this.currencyIsoCode});
  
  @override
  List<Object> get props => [currencyIsoCode];
}
class CurrencySymbolChanged extends CurrencyEvent {
  final String currencySymbol;
  
  const CurrencySymbolChanged({@required this.currencySymbol});
  
  @override
  List<Object> get props => [currencySymbol];
}
class CurrencyLogoUrlChanged extends CurrencyEvent {
  final String currencyLogoUrl;
  
  const CurrencyLogoUrlChanged({@required this.currencyLogoUrl});
  
  @override
  List<Object> get props => [currencyLogoUrl];
}
class HasExtraDataChanged extends CurrencyEvent {
  final bool hasExtraData;
  
  const HasExtraDataChanged({@required this.hasExtraData});
  
  @override
  List<Object> get props => [hasExtraData];
}

class CurrencyFormSubmitted extends CurrencyEvent {}

class LoadCurrencyByIdForEdit extends CurrencyEvent {
  final int id;

  const LoadCurrencyByIdForEdit({@required this.id});

  @override
  List<Object> get props => [id];
}

class DeleteCurrencyById extends CurrencyEvent {
  final int id;

  const DeleteCurrencyById({@required this.id});

  @override
  List<Object> get props => [id];
}

class LoadCurrencyByIdForView extends CurrencyEvent {
  final int id;

  const LoadCurrencyByIdForView({@required this.id});

  @override
  List<Object> get props => [id];
}
