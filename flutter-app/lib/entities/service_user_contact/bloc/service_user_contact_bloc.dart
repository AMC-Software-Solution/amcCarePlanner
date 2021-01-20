import 'dart:async';
import 'package:bloc/bloc.dart';
import 'package:flutter/foundation.dart';
import 'package:equatable/equatable.dart';
import 'package:flutter/widgets.dart';
import 'package:formz/formz.dart';

import 'package:amcCarePlanner/entities/service_user_contact/service_user_contact_model.dart';
import 'package:amcCarePlanner/entities/service_user_contact/service_user_contact_repository.dart';
import 'package:amcCarePlanner/entities/service_user_contact/bloc/service_user_contact_form_model.dart';
import 'package:amcCarePlanner/generated/l10n.dart';
import 'package:amcCarePlanner/shared/repository/http_utils.dart';
import 'package:intl/intl.dart';

part 'service_user_contact_events.dart';
part 'service_user_contact_state.dart';

class ServiceUserContactBloc extends Bloc<ServiceUserContactEvent, ServiceUserContactState> {
  final ServiceUserContactRepository _serviceUserContactRepository;

  final addressController = TextEditingController();
  final cityOrTownController = TextEditingController();
  final countyController = TextEditingController();
  final postCodeController = TextEditingController();
  final telephoneController = TextEditingController();
  final createdDateController = TextEditingController();
  final lastUpdatedDateController = TextEditingController();
  final clientIdController = TextEditingController();

  ServiceUserContactBloc({@required ServiceUserContactRepository serviceUserContactRepository}) : assert(serviceUserContactRepository != null),
        _serviceUserContactRepository = serviceUserContactRepository, 
  super(ServiceUserContactState(null,null,));

  @override
  void onTransition(Transition<ServiceUserContactEvent, ServiceUserContactState> transition) {
    super.onTransition(transition);
  }

  @override
  Stream<ServiceUserContactState> mapEventToState(ServiceUserContactEvent event) async* {
    if (event is InitServiceUserContactList) {
      yield* onInitList(event);
    } else if (event is ServiceUserContactFormSubmitted) {
      yield* onSubmit();
    } else if (event is LoadServiceUserContactByIdForEdit) {
      yield* onLoadServiceUserContactIdForEdit(event);
    } else if (event is DeleteServiceUserContactById) {
      yield* onDeleteServiceUserContactId(event);
    } else if (event is LoadServiceUserContactByIdForView) {
      yield* onLoadServiceUserContactIdForView(event);
    }else if (event is AddressChanged){
      yield* onAddressChange(event);
    }else if (event is CityOrTownChanged){
      yield* onCityOrTownChange(event);
    }else if (event is CountyChanged){
      yield* onCountyChange(event);
    }else if (event is PostCodeChanged){
      yield* onPostCodeChange(event);
    }else if (event is TelephoneChanged){
      yield* onTelephoneChange(event);
    }else if (event is CreatedDateChanged){
      yield* onCreatedDateChange(event);
    }else if (event is LastUpdatedDateChanged){
      yield* onLastUpdatedDateChange(event);
    }else if (event is ClientIdChanged){
      yield* onClientIdChange(event);
    }else if (event is HasExtraDataChanged){
      yield* onHasExtraDataChange(event);
    }  }

  Stream<ServiceUserContactState> onInitList(InitServiceUserContactList event) async* {
    yield this.state.copyWith(serviceUserContactStatusUI: ServiceUserContactStatusUI.loading);
    List<ServiceUserContact> serviceUserContacts = await _serviceUserContactRepository.getAllServiceUserContacts();
    yield this.state.copyWith(serviceUserContacts: serviceUserContacts, serviceUserContactStatusUI: ServiceUserContactStatusUI.done);
  }

  Stream<ServiceUserContactState> onSubmit() async* {
    if (this.state.formStatus.isValidated) {
      yield this.state.copyWith(formStatus: FormzStatus.submissionInProgress);
      try {
        ServiceUserContact result;
        if(this.state.editMode) {
          ServiceUserContact newServiceUserContact = ServiceUserContact(state.loadedServiceUserContact.id,
            this.state.address.value,  
            this.state.cityOrTown.value,  
            this.state.county.value,  
            this.state.postCode.value,  
            this.state.telephone.value,  
            this.state.createdDate.value,  
            this.state.lastUpdatedDate.value,  
            this.state.clientId.value,  
            this.state.hasExtraData.value,  
            null, 
          );

          result = await _serviceUserContactRepository.update(newServiceUserContact);
        } else {
          ServiceUserContact newServiceUserContact = ServiceUserContact(null,
            this.state.address.value,  
            this.state.cityOrTown.value,  
            this.state.county.value,  
            this.state.postCode.value,  
            this.state.telephone.value,  
            this.state.createdDate.value,  
            this.state.lastUpdatedDate.value,  
            this.state.clientId.value,  
            this.state.hasExtraData.value,  
            null, 
          );

          result = await _serviceUserContactRepository.create(newServiceUserContact);
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

  Stream<ServiceUserContactState> onLoadServiceUserContactIdForEdit(LoadServiceUserContactByIdForEdit event) async* {
    yield this.state.copyWith(serviceUserContactStatusUI: ServiceUserContactStatusUI.loading);
    ServiceUserContact loadedServiceUserContact = await _serviceUserContactRepository.getServiceUserContact(event.id);

    final address = AddressInput.dirty(loadedServiceUserContact?.address != null ? loadedServiceUserContact.address: '');
    final cityOrTown = CityOrTownInput.dirty(loadedServiceUserContact?.cityOrTown != null ? loadedServiceUserContact.cityOrTown: '');
    final county = CountyInput.dirty(loadedServiceUserContact?.county != null ? loadedServiceUserContact.county: '');
    final postCode = PostCodeInput.dirty(loadedServiceUserContact?.postCode != null ? loadedServiceUserContact.postCode: '');
    final telephone = TelephoneInput.dirty(loadedServiceUserContact?.telephone != null ? loadedServiceUserContact.telephone: '');
    final createdDate = CreatedDateInput.dirty(loadedServiceUserContact?.createdDate != null ? loadedServiceUserContact.createdDate: null);
    final lastUpdatedDate = LastUpdatedDateInput.dirty(loadedServiceUserContact?.lastUpdatedDate != null ? loadedServiceUserContact.lastUpdatedDate: null);
    final clientId = ClientIdInput.dirty(loadedServiceUserContact?.clientId != null ? loadedServiceUserContact.clientId: 0);
    final hasExtraData = HasExtraDataInput.dirty(loadedServiceUserContact?.hasExtraData != null ? loadedServiceUserContact.hasExtraData: false);

    yield this.state.copyWith(loadedServiceUserContact: loadedServiceUserContact, editMode: true,
      address: address,
      cityOrTown: cityOrTown,
      county: county,
      postCode: postCode,
      telephone: telephone,
      createdDate: createdDate,
      lastUpdatedDate: lastUpdatedDate,
      clientId: clientId,
      hasExtraData: hasExtraData,
    serviceUserContactStatusUI: ServiceUserContactStatusUI.done);

    addressController.text = loadedServiceUserContact.address;
    cityOrTownController.text = loadedServiceUserContact.cityOrTown;
    countyController.text = loadedServiceUserContact.county;
    postCodeController.text = loadedServiceUserContact.postCode;
    telephoneController.text = loadedServiceUserContact.telephone;
    createdDateController.text = DateFormat.yMMMMd(S.current.locale).format(loadedServiceUserContact?.createdDate);
    lastUpdatedDateController.text = DateFormat.yMMMMd(S.current.locale).format(loadedServiceUserContact?.lastUpdatedDate);
    clientIdController.text = loadedServiceUserContact.clientId.toString();
  }

  Stream<ServiceUserContactState> onDeleteServiceUserContactId(DeleteServiceUserContactById event) async* {
    try {
      await _serviceUserContactRepository.delete(event.id);
      this.add(InitServiceUserContactList());
      yield this.state.copyWith(deleteStatus: ServiceUserContactDeleteStatus.ok);
    } catch (e) {
      yield this.state.copyWith(deleteStatus: ServiceUserContactDeleteStatus.ko,
          generalNotificationKey: HttpUtils.errorServerKey);
    }
    yield this.state.copyWith(deleteStatus: ServiceUserContactDeleteStatus.none);
  }

  Stream<ServiceUserContactState> onLoadServiceUserContactIdForView(LoadServiceUserContactByIdForView event) async* {
    yield this.state.copyWith(serviceUserContactStatusUI: ServiceUserContactStatusUI.loading);
    try {
      ServiceUserContact loadedServiceUserContact = await _serviceUserContactRepository.getServiceUserContact(event.id);
      yield this.state.copyWith(loadedServiceUserContact: loadedServiceUserContact, serviceUserContactStatusUI: ServiceUserContactStatusUI.done);
    } catch(e) {
      yield this.state.copyWith(loadedServiceUserContact: null, serviceUserContactStatusUI: ServiceUserContactStatusUI.error);
    }
  }


  Stream<ServiceUserContactState> onAddressChange(AddressChanged event) async* {
    final address = AddressInput.dirty(event.address);
    yield this.state.copyWith(
      address: address,
      formStatus: Formz.validate([
        address,
      this.state.cityOrTown,
      this.state.county,
      this.state.postCode,
      this.state.telephone,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<ServiceUserContactState> onCityOrTownChange(CityOrTownChanged event) async* {
    final cityOrTown = CityOrTownInput.dirty(event.cityOrTown);
    yield this.state.copyWith(
      cityOrTown: cityOrTown,
      formStatus: Formz.validate([
      this.state.address,
        cityOrTown,
      this.state.county,
      this.state.postCode,
      this.state.telephone,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<ServiceUserContactState> onCountyChange(CountyChanged event) async* {
    final county = CountyInput.dirty(event.county);
    yield this.state.copyWith(
      county: county,
      formStatus: Formz.validate([
      this.state.address,
      this.state.cityOrTown,
        county,
      this.state.postCode,
      this.state.telephone,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<ServiceUserContactState> onPostCodeChange(PostCodeChanged event) async* {
    final postCode = PostCodeInput.dirty(event.postCode);
    yield this.state.copyWith(
      postCode: postCode,
      formStatus: Formz.validate([
      this.state.address,
      this.state.cityOrTown,
      this.state.county,
        postCode,
      this.state.telephone,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<ServiceUserContactState> onTelephoneChange(TelephoneChanged event) async* {
    final telephone = TelephoneInput.dirty(event.telephone);
    yield this.state.copyWith(
      telephone: telephone,
      formStatus: Formz.validate([
      this.state.address,
      this.state.cityOrTown,
      this.state.county,
      this.state.postCode,
        telephone,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<ServiceUserContactState> onCreatedDateChange(CreatedDateChanged event) async* {
    final createdDate = CreatedDateInput.dirty(event.createdDate);
    yield this.state.copyWith(
      createdDate: createdDate,
      formStatus: Formz.validate([
      this.state.address,
      this.state.cityOrTown,
      this.state.county,
      this.state.postCode,
      this.state.telephone,
        createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<ServiceUserContactState> onLastUpdatedDateChange(LastUpdatedDateChanged event) async* {
    final lastUpdatedDate = LastUpdatedDateInput.dirty(event.lastUpdatedDate);
    yield this.state.copyWith(
      lastUpdatedDate: lastUpdatedDate,
      formStatus: Formz.validate([
      this.state.address,
      this.state.cityOrTown,
      this.state.county,
      this.state.postCode,
      this.state.telephone,
      this.state.createdDate,
        lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<ServiceUserContactState> onClientIdChange(ClientIdChanged event) async* {
    final clientId = ClientIdInput.dirty(event.clientId);
    yield this.state.copyWith(
      clientId: clientId,
      formStatus: Formz.validate([
      this.state.address,
      this.state.cityOrTown,
      this.state.county,
      this.state.postCode,
      this.state.telephone,
      this.state.createdDate,
      this.state.lastUpdatedDate,
        clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<ServiceUserContactState> onHasExtraDataChange(HasExtraDataChanged event) async* {
    final hasExtraData = HasExtraDataInput.dirty(event.hasExtraData);
    yield this.state.copyWith(
      hasExtraData: hasExtraData,
      formStatus: Formz.validate([
      this.state.address,
      this.state.cityOrTown,
      this.state.county,
      this.state.postCode,
      this.state.telephone,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
        hasExtraData,
      ]),
    );
  }

  @override
  Future<void> close() {
    addressController.dispose();
    cityOrTownController.dispose();
    countyController.dispose();
    postCodeController.dispose();
    telephoneController.dispose();
    createdDateController.dispose();
    lastUpdatedDateController.dispose();
    clientIdController.dispose();
    return super.close();
  }

}