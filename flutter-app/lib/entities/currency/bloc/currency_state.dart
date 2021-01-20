part of 'currency_bloc.dart';

enum CurrencyStatusUI {init, loading, error, done}
enum CurrencyDeleteStatus {ok, ko, none}

class CurrencyState extends Equatable {
  final List<Currency> currencies;
  final Currency loadedCurrency;
  final bool editMode;
  final CurrencyDeleteStatus deleteStatus;
  final CurrencyStatusUI currencyStatusUI;

  final FormzStatus formStatus;
  final String generalNotificationKey;

  final CurrencyInput currency;
  final CurrencyIsoCodeInput currencyIsoCode;
  final CurrencySymbolInput currencySymbol;
  final CurrencyLogoUrlInput currencyLogoUrl;
  final HasExtraDataInput hasExtraData;

  
  CurrencyState(
{
    this.currencies = const [],
    this.currencyStatusUI = CurrencyStatusUI.init,
    this.loadedCurrency = const Currency(0,'','','','',false,),
    this.editMode = false,
    this.formStatus = FormzStatus.pure,
    this.generalNotificationKey = '',
    this.deleteStatus = CurrencyDeleteStatus.none,
    this.currency = const CurrencyInput.pure(),
    this.currencyIsoCode = const CurrencyIsoCodeInput.pure(),
    this.currencySymbol = const CurrencySymbolInput.pure(),
    this.currencyLogoUrl = const CurrencyLogoUrlInput.pure(),
    this.hasExtraData = const HasExtraDataInput.pure(),
  });

  CurrencyState copyWith({
    List<Currency> currencies,
    CurrencyStatusUI currencyStatusUI,
    bool editMode,
    CurrencyDeleteStatus deleteStatus,
    Currency loadedCurrency,
    FormzStatus formStatus,
    String generalNotificationKey,
    CurrencyInput currency,
    CurrencyIsoCodeInput currencyIsoCode,
    CurrencySymbolInput currencySymbol,
    CurrencyLogoUrlInput currencyLogoUrl,
    HasExtraDataInput hasExtraData,
  }) {
    return CurrencyState(
      currencies: currencies ?? this.currencies,
      currencyStatusUI: currencyStatusUI ?? this.currencyStatusUI,
      loadedCurrency: loadedCurrency ?? this.loadedCurrency,
      editMode: editMode ?? this.editMode,
      formStatus: formStatus ?? this.formStatus,
      generalNotificationKey: generalNotificationKey ?? this.generalNotificationKey,
      deleteStatus: deleteStatus ?? this.deleteStatus,
      currency: currency ?? this.currency,
      currencyIsoCode: currencyIsoCode ?? this.currencyIsoCode,
      currencySymbol: currencySymbol ?? this.currencySymbol,
      currencyLogoUrl: currencyLogoUrl ?? this.currencyLogoUrl,
      hasExtraData: hasExtraData ?? this.hasExtraData,
    );
  }

  @override
  List<Object> get props => [currencies, currencyStatusUI,
     loadedCurrency, editMode, deleteStatus, formStatus, generalNotificationKey, 
currency,currencyIsoCode,currencySymbol,currencyLogoUrl,hasExtraData,];

  @override
  bool get stringify => true;
}
