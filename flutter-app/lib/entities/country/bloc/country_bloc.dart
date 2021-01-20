import 'dart:async';
import 'package:bloc/bloc.dart';
import 'package:flutter/foundation.dart';
import 'package:equatable/equatable.dart';
import 'package:flutter/widgets.dart';
import 'package:formz/formz.dart';

import 'package:amcCarePlanner/entities/country/country_model.dart';
import 'package:amcCarePlanner/entities/country/country_repository.dart';
import 'package:amcCarePlanner/entities/country/bloc/country_form_model.dart';
import 'package:amcCarePlanner/generated/l10n.dart';
import 'package:amcCarePlanner/shared/repository/http_utils.dart';
import 'package:intl/intl.dart';

part 'country_events.dart';
part 'country_state.dart';

class CountryBloc extends Bloc<CountryEvent, CountryState> {
  final CountryRepository _countryRepository;

  final countryNameController = TextEditingController();
  final countryIsoCodeController = TextEditingController();
  final countryFlagUrlController = TextEditingController();
  final countryCallingCodeController = TextEditingController();
  final countryTelDigitLengthController = TextEditingController();
  final createdDateController = TextEditingController();
  final lastUpdatedDateController = TextEditingController();

  CountryBloc({@required CountryRepository countryRepository}) : assert(countryRepository != null),
        _countryRepository = countryRepository, 
  super(CountryState(null,null,));

  @override
  void onTransition(Transition<CountryEvent, CountryState> transition) {
    super.onTransition(transition);
  }

  @override
  Stream<CountryState> mapEventToState(CountryEvent event) async* {
    if (event is InitCountryList) {
      yield* onInitList(event);
    } else if (event is CountryFormSubmitted) {
      yield* onSubmit();
    } else if (event is LoadCountryByIdForEdit) {
      yield* onLoadCountryIdForEdit(event);
    } else if (event is DeleteCountryById) {
      yield* onDeleteCountryId(event);
    } else if (event is LoadCountryByIdForView) {
      yield* onLoadCountryIdForView(event);
    }else if (event is CountryNameChanged){
      yield* onCountryNameChange(event);
    }else if (event is CountryIsoCodeChanged){
      yield* onCountryIsoCodeChange(event);
    }else if (event is CountryFlagUrlChanged){
      yield* onCountryFlagUrlChange(event);
    }else if (event is CountryCallingCodeChanged){
      yield* onCountryCallingCodeChange(event);
    }else if (event is CountryTelDigitLengthChanged){
      yield* onCountryTelDigitLengthChange(event);
    }else if (event is CreatedDateChanged){
      yield* onCreatedDateChange(event);
    }else if (event is LastUpdatedDateChanged){
      yield* onLastUpdatedDateChange(event);
    }else if (event is HasExtraDataChanged){
      yield* onHasExtraDataChange(event);
    }  }

  Stream<CountryState> onInitList(InitCountryList event) async* {
    yield this.state.copyWith(countryStatusUI: CountryStatusUI.loading);
    List<Country> countries = await _countryRepository.getAllCountries();
    yield this.state.copyWith(countries: countries, countryStatusUI: CountryStatusUI.done);
  }

  Stream<CountryState> onSubmit() async* {
    if (this.state.formStatus.isValidated) {
      yield this.state.copyWith(formStatus: FormzStatus.submissionInProgress);
      try {
        Country result;
        if(this.state.editMode) {
          Country newCountry = Country(state.loadedCountry.id,
            this.state.countryName.value,  
            this.state.countryIsoCode.value,  
            this.state.countryFlagUrl.value,  
            this.state.countryCallingCode.value,  
            this.state.countryTelDigitLength.value,  
            this.state.createdDate.value,  
            this.state.lastUpdatedDate.value,  
            this.state.hasExtraData.value,  
          );

          result = await _countryRepository.update(newCountry);
        } else {
          Country newCountry = Country(null,
            this.state.countryName.value,  
            this.state.countryIsoCode.value,  
            this.state.countryFlagUrl.value,  
            this.state.countryCallingCode.value,  
            this.state.countryTelDigitLength.value,  
            this.state.createdDate.value,  
            this.state.lastUpdatedDate.value,  
            this.state.hasExtraData.value,  
          );

          result = await _countryRepository.create(newCountry);
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

  Stream<CountryState> onLoadCountryIdForEdit(LoadCountryByIdForEdit event) async* {
    yield this.state.copyWith(countryStatusUI: CountryStatusUI.loading);
    Country loadedCountry = await _countryRepository.getCountry(event.id);

    final countryName = CountryNameInput.dirty(loadedCountry?.countryName != null ? loadedCountry.countryName: '');
    final countryIsoCode = CountryIsoCodeInput.dirty(loadedCountry?.countryIsoCode != null ? loadedCountry.countryIsoCode: '');
    final countryFlagUrl = CountryFlagUrlInput.dirty(loadedCountry?.countryFlagUrl != null ? loadedCountry.countryFlagUrl: '');
    final countryCallingCode = CountryCallingCodeInput.dirty(loadedCountry?.countryCallingCode != null ? loadedCountry.countryCallingCode: '');
    final countryTelDigitLength = CountryTelDigitLengthInput.dirty(loadedCountry?.countryTelDigitLength != null ? loadedCountry.countryTelDigitLength: 0);
    final createdDate = CreatedDateInput.dirty(loadedCountry?.createdDate != null ? loadedCountry.createdDate: null);
    final lastUpdatedDate = LastUpdatedDateInput.dirty(loadedCountry?.lastUpdatedDate != null ? loadedCountry.lastUpdatedDate: null);
    final hasExtraData = HasExtraDataInput.dirty(loadedCountry?.hasExtraData != null ? loadedCountry.hasExtraData: false);

    yield this.state.copyWith(loadedCountry: loadedCountry, editMode: true,
      countryName: countryName,
      countryIsoCode: countryIsoCode,
      countryFlagUrl: countryFlagUrl,
      countryCallingCode: countryCallingCode,
      countryTelDigitLength: countryTelDigitLength,
      createdDate: createdDate,
      lastUpdatedDate: lastUpdatedDate,
      hasExtraData: hasExtraData,
    countryStatusUI: CountryStatusUI.done);

    countryNameController.text = loadedCountry.countryName;
    countryIsoCodeController.text = loadedCountry.countryIsoCode;
    countryFlagUrlController.text = loadedCountry.countryFlagUrl;
    countryCallingCodeController.text = loadedCountry.countryCallingCode;
    countryTelDigitLengthController.text = loadedCountry.countryTelDigitLength.toString();
    createdDateController.text = DateFormat.yMMMMd(S.current.locale).format(loadedCountry?.createdDate);
    lastUpdatedDateController.text = DateFormat.yMMMMd(S.current.locale).format(loadedCountry?.lastUpdatedDate);
  }

  Stream<CountryState> onDeleteCountryId(DeleteCountryById event) async* {
    try {
      await _countryRepository.delete(event.id);
      this.add(InitCountryList());
      yield this.state.copyWith(deleteStatus: CountryDeleteStatus.ok);
    } catch (e) {
      yield this.state.copyWith(deleteStatus: CountryDeleteStatus.ko,
          generalNotificationKey: HttpUtils.errorServerKey);
    }
    yield this.state.copyWith(deleteStatus: CountryDeleteStatus.none);
  }

  Stream<CountryState> onLoadCountryIdForView(LoadCountryByIdForView event) async* {
    yield this.state.copyWith(countryStatusUI: CountryStatusUI.loading);
    try {
      Country loadedCountry = await _countryRepository.getCountry(event.id);
      yield this.state.copyWith(loadedCountry: loadedCountry, countryStatusUI: CountryStatusUI.done);
    } catch(e) {
      yield this.state.copyWith(loadedCountry: null, countryStatusUI: CountryStatusUI.error);
    }
  }


  Stream<CountryState> onCountryNameChange(CountryNameChanged event) async* {
    final countryName = CountryNameInput.dirty(event.countryName);
    yield this.state.copyWith(
      countryName: countryName,
      formStatus: Formz.validate([
        countryName,
      this.state.countryIsoCode,
      this.state.countryFlagUrl,
      this.state.countryCallingCode,
      this.state.countryTelDigitLength,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<CountryState> onCountryIsoCodeChange(CountryIsoCodeChanged event) async* {
    final countryIsoCode = CountryIsoCodeInput.dirty(event.countryIsoCode);
    yield this.state.copyWith(
      countryIsoCode: countryIsoCode,
      formStatus: Formz.validate([
      this.state.countryName,
        countryIsoCode,
      this.state.countryFlagUrl,
      this.state.countryCallingCode,
      this.state.countryTelDigitLength,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<CountryState> onCountryFlagUrlChange(CountryFlagUrlChanged event) async* {
    final countryFlagUrl = CountryFlagUrlInput.dirty(event.countryFlagUrl);
    yield this.state.copyWith(
      countryFlagUrl: countryFlagUrl,
      formStatus: Formz.validate([
      this.state.countryName,
      this.state.countryIsoCode,
        countryFlagUrl,
      this.state.countryCallingCode,
      this.state.countryTelDigitLength,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<CountryState> onCountryCallingCodeChange(CountryCallingCodeChanged event) async* {
    final countryCallingCode = CountryCallingCodeInput.dirty(event.countryCallingCode);
    yield this.state.copyWith(
      countryCallingCode: countryCallingCode,
      formStatus: Formz.validate([
      this.state.countryName,
      this.state.countryIsoCode,
      this.state.countryFlagUrl,
        countryCallingCode,
      this.state.countryTelDigitLength,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<CountryState> onCountryTelDigitLengthChange(CountryTelDigitLengthChanged event) async* {
    final countryTelDigitLength = CountryTelDigitLengthInput.dirty(event.countryTelDigitLength);
    yield this.state.copyWith(
      countryTelDigitLength: countryTelDigitLength,
      formStatus: Formz.validate([
      this.state.countryName,
      this.state.countryIsoCode,
      this.state.countryFlagUrl,
      this.state.countryCallingCode,
        countryTelDigitLength,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<CountryState> onCreatedDateChange(CreatedDateChanged event) async* {
    final createdDate = CreatedDateInput.dirty(event.createdDate);
    yield this.state.copyWith(
      createdDate: createdDate,
      formStatus: Formz.validate([
      this.state.countryName,
      this.state.countryIsoCode,
      this.state.countryFlagUrl,
      this.state.countryCallingCode,
      this.state.countryTelDigitLength,
        createdDate,
      this.state.lastUpdatedDate,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<CountryState> onLastUpdatedDateChange(LastUpdatedDateChanged event) async* {
    final lastUpdatedDate = LastUpdatedDateInput.dirty(event.lastUpdatedDate);
    yield this.state.copyWith(
      lastUpdatedDate: lastUpdatedDate,
      formStatus: Formz.validate([
      this.state.countryName,
      this.state.countryIsoCode,
      this.state.countryFlagUrl,
      this.state.countryCallingCode,
      this.state.countryTelDigitLength,
      this.state.createdDate,
        lastUpdatedDate,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<CountryState> onHasExtraDataChange(HasExtraDataChanged event) async* {
    final hasExtraData = HasExtraDataInput.dirty(event.hasExtraData);
    yield this.state.copyWith(
      hasExtraData: hasExtraData,
      formStatus: Formz.validate([
      this.state.countryName,
      this.state.countryIsoCode,
      this.state.countryFlagUrl,
      this.state.countryCallingCode,
      this.state.countryTelDigitLength,
      this.state.createdDate,
      this.state.lastUpdatedDate,
        hasExtraData,
      ]),
    );
  }

  @override
  Future<void> close() {
    countryNameController.dispose();
    countryIsoCodeController.dispose();
    countryFlagUrlController.dispose();
    countryCallingCodeController.dispose();
    countryTelDigitLengthController.dispose();
    createdDateController.dispose();
    lastUpdatedDateController.dispose();
    return super.close();
  }

}