import 'dart:async';
import 'package:bloc/bloc.dart';
import 'package:flutter/foundation.dart';
import 'package:equatable/equatable.dart';
import 'package:flutter/widgets.dart';
import 'package:formz/formz.dart';

import 'package:amcCarePlanner/entities/consent/consent_model.dart';
import 'package:amcCarePlanner/entities/consent/consent_repository.dart';
import 'package:amcCarePlanner/entities/consent/bloc/consent_form_model.dart';
import 'package:amcCarePlanner/generated/l10n.dart';
import 'package:amcCarePlanner/shared/repository/http_utils.dart';
import 'package:intl/intl.dart';

part 'consent_events.dart';
part 'consent_state.dart';

class ConsentBloc extends Bloc<ConsentEvent, ConsentState> {
  final ConsentRepository _consentRepository;

  final titleController = TextEditingController();
  final descriptionController = TextEditingController();
  final arrangementsController = TextEditingController();
  final serviceUserSignatureController = TextEditingController();
  final signatureImageUrlController = TextEditingController();
  final consentDateController = TextEditingController();
  final createdDateController = TextEditingController();
  final lastUpdatedDateController = TextEditingController();
  final clientIdController = TextEditingController();

  ConsentBloc({@required ConsentRepository consentRepository}) : assert(consentRepository != null),
        _consentRepository = consentRepository, 
  super(ConsentState(null,null,null,));

  @override
  void onTransition(Transition<ConsentEvent, ConsentState> transition) {
    super.onTransition(transition);
  }

  @override
  Stream<ConsentState> mapEventToState(ConsentEvent event) async* {
    if (event is InitConsentList) {
      yield* onInitList(event);
    } else if (event is ConsentFormSubmitted) {
      yield* onSubmit();
    } else if (event is LoadConsentByIdForEdit) {
      yield* onLoadConsentIdForEdit(event);
    } else if (event is DeleteConsentById) {
      yield* onDeleteConsentId(event);
    } else if (event is LoadConsentByIdForView) {
      yield* onLoadConsentIdForView(event);
    }else if (event is TitleChanged){
      yield* onTitleChange(event);
    }else if (event is DescriptionChanged){
      yield* onDescriptionChange(event);
    }else if (event is GiveConsentChanged){
      yield* onGiveConsentChange(event);
    }else if (event is ArrangementsChanged){
      yield* onArrangementsChange(event);
    }else if (event is ServiceUserSignatureChanged){
      yield* onServiceUserSignatureChange(event);
    }else if (event is SignatureImageUrlChanged){
      yield* onSignatureImageUrlChange(event);
    }else if (event is ConsentDateChanged){
      yield* onConsentDateChange(event);
    }else if (event is CreatedDateChanged){
      yield* onCreatedDateChange(event);
    }else if (event is LastUpdatedDateChanged){
      yield* onLastUpdatedDateChange(event);
    }else if (event is ClientIdChanged){
      yield* onClientIdChange(event);
    }else if (event is HasExtraDataChanged){
      yield* onHasExtraDataChange(event);
    }  }

  Stream<ConsentState> onInitList(InitConsentList event) async* {
    yield this.state.copyWith(consentStatusUI: ConsentStatusUI.loading);
    List<Consent> consents = await _consentRepository.getAllConsents();
    yield this.state.copyWith(consents: consents, consentStatusUI: ConsentStatusUI.done);
  }

  Stream<ConsentState> onSubmit() async* {
    if (this.state.formStatus.isValidated) {
      yield this.state.copyWith(formStatus: FormzStatus.submissionInProgress);
      try {
        Consent result;
        if(this.state.editMode) {
          Consent newConsent = Consent(state.loadedConsent.id,
            this.state.title.value,  
            this.state.description.value,  
            this.state.giveConsent.value,  
            this.state.arrangements.value,  
            this.state.serviceUserSignature.value,  
            this.state.signatureImageUrl.value,  
            this.state.consentDate.value,  
            this.state.createdDate.value,  
            this.state.lastUpdatedDate.value,  
            this.state.clientId.value,  
            this.state.hasExtraData.value,  
            null, 
            null, 
          );

          result = await _consentRepository.update(newConsent);
        } else {
          Consent newConsent = Consent(null,
            this.state.title.value,  
            this.state.description.value,  
            this.state.giveConsent.value,  
            this.state.arrangements.value,  
            this.state.serviceUserSignature.value,  
            this.state.signatureImageUrl.value,  
            this.state.consentDate.value,  
            this.state.createdDate.value,  
            this.state.lastUpdatedDate.value,  
            this.state.clientId.value,  
            this.state.hasExtraData.value,  
            null, 
            null, 
          );

          result = await _consentRepository.create(newConsent);
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

  Stream<ConsentState> onLoadConsentIdForEdit(LoadConsentByIdForEdit event) async* {
    yield this.state.copyWith(consentStatusUI: ConsentStatusUI.loading);
    Consent loadedConsent = await _consentRepository.getConsent(event.id);

    final title = TitleInput.dirty(loadedConsent?.title != null ? loadedConsent.title: '');
    final description = DescriptionInput.dirty(loadedConsent?.description != null ? loadedConsent.description: '');
    final giveConsent = GiveConsentInput.dirty(loadedConsent?.giveConsent != null ? loadedConsent.giveConsent: false);
    final arrangements = ArrangementsInput.dirty(loadedConsent?.arrangements != null ? loadedConsent.arrangements: '');
    final serviceUserSignature = ServiceUserSignatureInput.dirty(loadedConsent?.serviceUserSignature != null ? loadedConsent.serviceUserSignature: '');
    final signatureImageUrl = SignatureImageUrlInput.dirty(loadedConsent?.signatureImageUrl != null ? loadedConsent.signatureImageUrl: '');
    final consentDate = ConsentDateInput.dirty(loadedConsent?.consentDate != null ? loadedConsent.consentDate: null);
    final createdDate = CreatedDateInput.dirty(loadedConsent?.createdDate != null ? loadedConsent.createdDate: null);
    final lastUpdatedDate = LastUpdatedDateInput.dirty(loadedConsent?.lastUpdatedDate != null ? loadedConsent.lastUpdatedDate: null);
    final clientId = ClientIdInput.dirty(loadedConsent?.clientId != null ? loadedConsent.clientId: 0);
    final hasExtraData = HasExtraDataInput.dirty(loadedConsent?.hasExtraData != null ? loadedConsent.hasExtraData: false);

    yield this.state.copyWith(loadedConsent: loadedConsent, editMode: true,
      title: title,
      description: description,
      giveConsent: giveConsent,
      arrangements: arrangements,
      serviceUserSignature: serviceUserSignature,
      signatureImageUrl: signatureImageUrl,
      consentDate: consentDate,
      createdDate: createdDate,
      lastUpdatedDate: lastUpdatedDate,
      clientId: clientId,
      hasExtraData: hasExtraData,
    consentStatusUI: ConsentStatusUI.done);

    titleController.text = loadedConsent.title;
    descriptionController.text = loadedConsent.description;
    arrangementsController.text = loadedConsent.arrangements;
    serviceUserSignatureController.text = loadedConsent.serviceUserSignature;
    signatureImageUrlController.text = loadedConsent.signatureImageUrl;
    consentDateController.text = DateFormat.yMMMMd(S.current.locale).format(loadedConsent?.consentDate);
    createdDateController.text = DateFormat.yMMMMd(S.current.locale).format(loadedConsent?.createdDate);
    lastUpdatedDateController.text = DateFormat.yMMMMd(S.current.locale).format(loadedConsent?.lastUpdatedDate);
    clientIdController.text = loadedConsent.clientId.toString();
  }

  Stream<ConsentState> onDeleteConsentId(DeleteConsentById event) async* {
    try {
      await _consentRepository.delete(event.id);
      this.add(InitConsentList());
      yield this.state.copyWith(deleteStatus: ConsentDeleteStatus.ok);
    } catch (e) {
      yield this.state.copyWith(deleteStatus: ConsentDeleteStatus.ko,
          generalNotificationKey: HttpUtils.errorServerKey);
    }
    yield this.state.copyWith(deleteStatus: ConsentDeleteStatus.none);
  }

  Stream<ConsentState> onLoadConsentIdForView(LoadConsentByIdForView event) async* {
    yield this.state.copyWith(consentStatusUI: ConsentStatusUI.loading);
    try {
      Consent loadedConsent = await _consentRepository.getConsent(event.id);
      yield this.state.copyWith(loadedConsent: loadedConsent, consentStatusUI: ConsentStatusUI.done);
    } catch(e) {
      yield this.state.copyWith(loadedConsent: null, consentStatusUI: ConsentStatusUI.error);
    }
  }


  Stream<ConsentState> onTitleChange(TitleChanged event) async* {
    final title = TitleInput.dirty(event.title);
    yield this.state.copyWith(
      title: title,
      formStatus: Formz.validate([
        title,
      this.state.description,
      this.state.giveConsent,
      this.state.arrangements,
      this.state.serviceUserSignature,
      this.state.signatureImageUrl,
      this.state.consentDate,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<ConsentState> onDescriptionChange(DescriptionChanged event) async* {
    final description = DescriptionInput.dirty(event.description);
    yield this.state.copyWith(
      description: description,
      formStatus: Formz.validate([
      this.state.title,
        description,
      this.state.giveConsent,
      this.state.arrangements,
      this.state.serviceUserSignature,
      this.state.signatureImageUrl,
      this.state.consentDate,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<ConsentState> onGiveConsentChange(GiveConsentChanged event) async* {
    final giveConsent = GiveConsentInput.dirty(event.giveConsent);
    yield this.state.copyWith(
      giveConsent: giveConsent,
      formStatus: Formz.validate([
      this.state.title,
      this.state.description,
        giveConsent,
      this.state.arrangements,
      this.state.serviceUserSignature,
      this.state.signatureImageUrl,
      this.state.consentDate,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<ConsentState> onArrangementsChange(ArrangementsChanged event) async* {
    final arrangements = ArrangementsInput.dirty(event.arrangements);
    yield this.state.copyWith(
      arrangements: arrangements,
      formStatus: Formz.validate([
      this.state.title,
      this.state.description,
      this.state.giveConsent,
        arrangements,
      this.state.serviceUserSignature,
      this.state.signatureImageUrl,
      this.state.consentDate,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<ConsentState> onServiceUserSignatureChange(ServiceUserSignatureChanged event) async* {
    final serviceUserSignature = ServiceUserSignatureInput.dirty(event.serviceUserSignature);
    yield this.state.copyWith(
      serviceUserSignature: serviceUserSignature,
      formStatus: Formz.validate([
      this.state.title,
      this.state.description,
      this.state.giveConsent,
      this.state.arrangements,
        serviceUserSignature,
      this.state.signatureImageUrl,
      this.state.consentDate,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<ConsentState> onSignatureImageUrlChange(SignatureImageUrlChanged event) async* {
    final signatureImageUrl = SignatureImageUrlInput.dirty(event.signatureImageUrl);
    yield this.state.copyWith(
      signatureImageUrl: signatureImageUrl,
      formStatus: Formz.validate([
      this.state.title,
      this.state.description,
      this.state.giveConsent,
      this.state.arrangements,
      this.state.serviceUserSignature,
        signatureImageUrl,
      this.state.consentDate,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<ConsentState> onConsentDateChange(ConsentDateChanged event) async* {
    final consentDate = ConsentDateInput.dirty(event.consentDate);
    yield this.state.copyWith(
      consentDate: consentDate,
      formStatus: Formz.validate([
      this.state.title,
      this.state.description,
      this.state.giveConsent,
      this.state.arrangements,
      this.state.serviceUserSignature,
      this.state.signatureImageUrl,
        consentDate,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<ConsentState> onCreatedDateChange(CreatedDateChanged event) async* {
    final createdDate = CreatedDateInput.dirty(event.createdDate);
    yield this.state.copyWith(
      createdDate: createdDate,
      formStatus: Formz.validate([
      this.state.title,
      this.state.description,
      this.state.giveConsent,
      this.state.arrangements,
      this.state.serviceUserSignature,
      this.state.signatureImageUrl,
      this.state.consentDate,
        createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<ConsentState> onLastUpdatedDateChange(LastUpdatedDateChanged event) async* {
    final lastUpdatedDate = LastUpdatedDateInput.dirty(event.lastUpdatedDate);
    yield this.state.copyWith(
      lastUpdatedDate: lastUpdatedDate,
      formStatus: Formz.validate([
      this.state.title,
      this.state.description,
      this.state.giveConsent,
      this.state.arrangements,
      this.state.serviceUserSignature,
      this.state.signatureImageUrl,
      this.state.consentDate,
      this.state.createdDate,
        lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<ConsentState> onClientIdChange(ClientIdChanged event) async* {
    final clientId = ClientIdInput.dirty(event.clientId);
    yield this.state.copyWith(
      clientId: clientId,
      formStatus: Formz.validate([
      this.state.title,
      this.state.description,
      this.state.giveConsent,
      this.state.arrangements,
      this.state.serviceUserSignature,
      this.state.signatureImageUrl,
      this.state.consentDate,
      this.state.createdDate,
      this.state.lastUpdatedDate,
        clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<ConsentState> onHasExtraDataChange(HasExtraDataChanged event) async* {
    final hasExtraData = HasExtraDataInput.dirty(event.hasExtraData);
    yield this.state.copyWith(
      hasExtraData: hasExtraData,
      formStatus: Formz.validate([
      this.state.title,
      this.state.description,
      this.state.giveConsent,
      this.state.arrangements,
      this.state.serviceUserSignature,
      this.state.signatureImageUrl,
      this.state.consentDate,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
        hasExtraData,
      ]),
    );
  }

  @override
  Future<void> close() {
    titleController.dispose();
    descriptionController.dispose();
    arrangementsController.dispose();
    serviceUserSignatureController.dispose();
    signatureImageUrlController.dispose();
    consentDateController.dispose();
    createdDateController.dispose();
    lastUpdatedDateController.dispose();
    clientIdController.dispose();
    return super.close();
  }

}