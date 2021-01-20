import 'dart:async';
import 'package:bloc/bloc.dart';
import 'package:flutter/foundation.dart';
import 'package:equatable/equatable.dart';
import 'package:flutter/widgets.dart';
import 'package:formz/formz.dart';

import 'package:amcCarePlanner/entities/currency/currency_model.dart';
import 'package:amcCarePlanner/entities/currency/currency_repository.dart';
import 'package:amcCarePlanner/entities/currency/bloc/currency_form_model.dart';
import 'package:amcCarePlanner/generated/l10n.dart';
import 'package:amcCarePlanner/shared/repository/http_utils.dart';
import 'package:intl/intl.dart';

part 'currency_events.dart';
part 'currency_state.dart';

class CurrencyBloc extends Bloc<CurrencyEvent, CurrencyState> {
  final CurrencyRepository _currencyRepository;

  final currencyController = TextEditingController();
  final currencyIsoCodeController = TextEditingController();
  final currencySymbolController = TextEditingController();
  final currencyLogoUrlController = TextEditingController();

  CurrencyBloc({@required CurrencyRepository currencyRepository}) : assert(currencyRepository != null),
        _currencyRepository = currencyRepository, 
  super(CurrencyState());

  @override
  void onTransition(Transition<CurrencyEvent, CurrencyState> transition) {
    super.onTransition(transition);
  }

  @override
  Stream<CurrencyState> mapEventToState(CurrencyEvent event) async* {
    if (event is InitCurrencyList) {
      yield* onInitList(event);
    } else if (event is CurrencyFormSubmitted) {
      yield* onSubmit();
    } else if (event is LoadCurrencyByIdForEdit) {
      yield* onLoadCurrencyIdForEdit(event);
    } else if (event is DeleteCurrencyById) {
      yield* onDeleteCurrencyId(event);
    } else if (event is LoadCurrencyByIdForView) {
      yield* onLoadCurrencyIdForView(event);
    }else if (event is CurrencyChanged){
      yield* onCurrencyChange(event);
    }else if (event is CurrencyIsoCodeChanged){
      yield* onCurrencyIsoCodeChange(event);
    }else if (event is CurrencySymbolChanged){
      yield* onCurrencySymbolChange(event);
    }else if (event is CurrencyLogoUrlChanged){
      yield* onCurrencyLogoUrlChange(event);
    }else if (event is HasExtraDataChanged){
      yield* onHasExtraDataChange(event);
    }  }

  Stream<CurrencyState> onInitList(InitCurrencyList event) async* {
    yield this.state.copyWith(currencyStatusUI: CurrencyStatusUI.loading);
    List<Currency> currencies = await _currencyRepository.getAllCurrencies();
    yield this.state.copyWith(currencies: currencies, currencyStatusUI: CurrencyStatusUI.done);
  }

  Stream<CurrencyState> onSubmit() async* {
    if (this.state.formStatus.isValidated) {
      yield this.state.copyWith(formStatus: FormzStatus.submissionInProgress);
      try {
        Currency result;
        if(this.state.editMode) {
          Currency newCurrency = Currency(state.loadedCurrency.id,
            this.state.currency.value,  
            this.state.currencyIsoCode.value,  
            this.state.currencySymbol.value,  
            this.state.currencyLogoUrl.value,  
            this.state.hasExtraData.value,  
          );

          result = await _currencyRepository.update(newCurrency);
        } else {
          Currency newCurrency = Currency(null,
            this.state.currency.value,  
            this.state.currencyIsoCode.value,  
            this.state.currencySymbol.value,  
            this.state.currencyLogoUrl.value,  
            this.state.hasExtraData.value,  
          );

          result = await _currencyRepository.create(newCurrency);
        }

        if (result == null) {
          yield this.state.copyWith(formStatus: FormzStatus.submissionFailure,
              generalNotificationKey: HttpUtils.badRequestServerKey);
        } else {
          yield this.state.copyWith(formStatus: FormzStatus.submissionSuccess,
              generalNotificationKey: HttpUtils.successResult);
        }
      } catch (e) {
        yield this.state.copyWith(formStatus: FormzStatus.submissionFailure,
            generalNotificationKey: HttpUtils.errorServerKey);
      }
    }
  }

  Stream<CurrencyState> onLoadCurrencyIdForEdit(LoadCurrencyByIdForEdit event) async* {
    yield this.state.copyWith(currencyStatusUI: CurrencyStatusUI.loading);
    Currency loadedCurrency = await _currencyRepository.getCurrency(event.id);

    final currency = CurrencyInput.dirty(loadedCurrency?.currency != null ? loadedCurrency.currency: '');
    final currencyIsoCode = CurrencyIsoCodeInput.dirty(loadedCurrency?.currencyIsoCode != null ? loadedCurrency.currencyIsoCode: '');
    final currencySymbol = CurrencySymbolInput.dirty(loadedCurrency?.currencySymbol != null ? loadedCurrency.currencySymbol: '');
    final currencyLogoUrl = CurrencyLogoUrlInput.dirty(loadedCurrency?.currencyLogoUrl != null ? loadedCurrency.currencyLogoUrl: '');
    final hasExtraData = HasExtraDataInput.dirty(loadedCurrency?.hasExtraData != null ? loadedCurrency.hasExtraData: false);

    yield this.state.copyWith(loadedCurrency: loadedCurrency, editMode: true,
      currency: currency,
      currencyIsoCode: currencyIsoCode,
      currencySymbol: currencySymbol,
      currencyLogoUrl: currencyLogoUrl,
      hasExtraData: hasExtraData,
    currencyStatusUI: CurrencyStatusUI.done);

    currencyController.text = loadedCurrency.currency;
    currencyIsoCodeController.text = loadedCurrency.currencyIsoCode;
    currencySymbolController.text = loadedCurrency.currencySymbol;
    currencyLogoUrlController.text = loadedCurrency.currencyLogoUrl;
  }

  Stream<CurrencyState> onDeleteCurrencyId(DeleteCurrencyById event) async* {
    try {
      await _currencyRepository.delete(event.id);
      this.add(InitCurrencyList());
      yield this.state.copyWith(deleteStatus: CurrencyDeleteStatus.ok);
    } catch (e) {
      yield this.state.copyWith(deleteStatus: CurrencyDeleteStatus.ko,
          generalNotificationKey: HttpUtils.errorServerKey);
    }
    yield this.state.copyWith(deleteStatus: CurrencyDeleteStatus.none);
  }

  Stream<CurrencyState> onLoadCurrencyIdForView(LoadCurrencyByIdForView event) async* {
    yield this.state.copyWith(currencyStatusUI: CurrencyStatusUI.loading);
    try {
      Currency loadedCurrency = await _currencyRepository.getCurrency(event.id);
      yield this.state.copyWith(loadedCurrency: loadedCurrency, currencyStatusUI: CurrencyStatusUI.done);
    } catch(e) {
      yield this.state.copyWith(loadedCurrency: null, currencyStatusUI: CurrencyStatusUI.error);
    }
  }


  Stream<CurrencyState> onCurrencyChange(CurrencyChanged event) async* {
    final currency = CurrencyInput.dirty(event.currency);
    yield this.state.copyWith(
      currency: currency,
      formStatus: Formz.validate([
        currency,
      this.state.currencyIsoCode,
      this.state.currencySymbol,
      this.state.currencyLogoUrl,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<CurrencyState> onCurrencyIsoCodeChange(CurrencyIsoCodeChanged event) async* {
    final currencyIsoCode = CurrencyIsoCodeInput.dirty(event.currencyIsoCode);
    yield this.state.copyWith(
      currencyIsoCode: currencyIsoCode,
      formStatus: Formz.validate([
      this.state.currency,
        currencyIsoCode,
      this.state.currencySymbol,
      this.state.currencyLogoUrl,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<CurrencyState> onCurrencySymbolChange(CurrencySymbolChanged event) async* {
    final currencySymbol = CurrencySymbolInput.dirty(event.currencySymbol);
    yield this.state.copyWith(
      currencySymbol: currencySymbol,
      formStatus: Formz.validate([
      this.state.currency,
      this.state.currencyIsoCode,
        currencySymbol,
      this.state.currencyLogoUrl,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<CurrencyState> onCurrencyLogoUrlChange(CurrencyLogoUrlChanged event) async* {
    final currencyLogoUrl = CurrencyLogoUrlInput.dirty(event.currencyLogoUrl);
    yield this.state.copyWith(
      currencyLogoUrl: currencyLogoUrl,
      formStatus: Formz.validate([
      this.state.currency,
      this.state.currencyIsoCode,
      this.state.currencySymbol,
        currencyLogoUrl,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<CurrencyState> onHasExtraDataChange(HasExtraDataChanged event) async* {
    final hasExtraData = HasExtraDataInput.dirty(event.hasExtraData);
    yield this.state.copyWith(
      hasExtraData: hasExtraData,
      formStatus: Formz.validate([
      this.state.currency,
      this.state.currencyIsoCode,
      this.state.currencySymbol,
      this.state.currencyLogoUrl,
        hasExtraData,
      ]),
    );
  }

  @override
  Future<void> close() {
    currencyController.dispose();
    currencyIsoCodeController.dispose();
    currencySymbolController.dispose();
    currencyLogoUrlController.dispose();
    return super.close();
  }

}