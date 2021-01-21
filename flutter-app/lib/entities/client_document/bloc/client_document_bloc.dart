import 'dart:async';
import 'package:bloc/bloc.dart';
import 'package:flutter/foundation.dart';
import 'package:equatable/equatable.dart';
import 'package:flutter/widgets.dart';
import 'package:formz/formz.dart';

import 'package:amcCarePlanner/entities/client_document/client_document_model.dart';
import 'package:amcCarePlanner/entities/client_document/client_document_repository.dart';
import 'package:amcCarePlanner/entities/client_document/bloc/client_document_form_model.dart';
import 'package:amcCarePlanner/generated/l10n.dart';
import 'package:amcCarePlanner/shared/repository/http_utils.dart';
import 'package:intl/intl.dart';

part 'client_document_events.dart';
part 'client_document_state.dart';

class ClientDocumentBloc extends Bloc<ClientDocumentEvent, ClientDocumentState> {
  final ClientDocumentRepository _clientDocumentRepository;

  final documentNameController = TextEditingController();
  final documentNumberController = TextEditingController();
  final noteController = TextEditingController();
  final issuedDateController = TextEditingController();
  final expiryDateController = TextEditingController();
  final uploadedDateController = TextEditingController();
  final documentFileUrlController = TextEditingController();
  final createdDateController = TextEditingController();
  final lastUpdatedDateController = TextEditingController();
  final clientIdController = TextEditingController();

  ClientDocumentBloc({@required ClientDocumentRepository clientDocumentRepository}) : assert(clientDocumentRepository != null),
        _clientDocumentRepository = clientDocumentRepository, 
  super(ClientDocumentState(null,null,null,null,null,));

  @override
  void onTransition(Transition<ClientDocumentEvent, ClientDocumentState> transition) {
    super.onTransition(transition);
  }

  @override
  Stream<ClientDocumentState> mapEventToState(ClientDocumentEvent event) async* {
    if (event is InitClientDocumentList) {
      yield* onInitList(event);
    } else if (event is ClientDocumentFormSubmitted) {
      yield* onSubmit();
    } else if (event is LoadClientDocumentByIdForEdit) {
      yield* onLoadClientDocumentIdForEdit(event);
    } else if (event is DeleteClientDocumentById) {
      yield* onDeleteClientDocumentId(event);
    } else if (event is LoadClientDocumentByIdForView) {
      yield* onLoadClientDocumentIdForView(event);
    }else if (event is DocumentNameChanged){
      yield* onDocumentNameChange(event);
    }else if (event is DocumentNumberChanged){
      yield* onDocumentNumberChange(event);
    }else if (event is DocumentTypeChanged){
      yield* onDocumentTypeChange(event);
    }else if (event is DocumentStatusChanged){
      yield* onDocumentStatusChange(event);
    }else if (event is NoteChanged){
      yield* onNoteChange(event);
    }else if (event is IssuedDateChanged){
      yield* onIssuedDateChange(event);
    }else if (event is ExpiryDateChanged){
      yield* onExpiryDateChange(event);
    }else if (event is UploadedDateChanged){
      yield* onUploadedDateChange(event);
    }else if (event is DocumentFileUrlChanged){
      yield* onDocumentFileUrlChange(event);
    }else if (event is CreatedDateChanged){
      yield* onCreatedDateChange(event);
    }else if (event is LastUpdatedDateChanged){
      yield* onLastUpdatedDateChange(event);
    }else if (event is ClientIdChanged){
      yield* onClientIdChange(event);
    }else if (event is HasExtraDataChanged){
      yield* onHasExtraDataChange(event);
    }  }

  Stream<ClientDocumentState> onInitList(InitClientDocumentList event) async* {
    yield this.state.copyWith(clientDocumentStatusUI: ClientDocumentStatusUI.loading);
    List<ClientDocument> clientDocuments = await _clientDocumentRepository.getAllClientDocuments();
    yield this.state.copyWith(clientDocuments: clientDocuments, clientDocumentStatusUI: ClientDocumentStatusUI.done);
  }

  Stream<ClientDocumentState> onSubmit() async* {
    if (this.state.formStatus.isValidated) {
      yield this.state.copyWith(formStatus: FormzStatus.submissionInProgress);
      try {
        ClientDocument result;
        if(this.state.editMode) {
          ClientDocument newClientDocument = ClientDocument(state.loadedClientDocument.id,
            this.state.documentName.value,  
            this.state.documentNumber.value,  
            this.state.documentType.value,  
            this.state.documentStatus.value,  
            this.state.note.value,  
            this.state.issuedDate.value,  
            this.state.expiryDate.value,  
            this.state.uploadedDate.value,  
            this.state.documentFileUrl.value,  
            this.state.createdDate.value,  
            this.state.lastUpdatedDate.value,  
            this.state.clientId.value,  
            this.state.hasExtraData.value,  
            null, 
            null, 
          );

          result = await _clientDocumentRepository.update(newClientDocument);
        } else {
          ClientDocument newClientDocument = ClientDocument(null,
            this.state.documentName.value,  
            this.state.documentNumber.value,  
            this.state.documentType.value,  
            this.state.documentStatus.value,  
            this.state.note.value,  
            this.state.issuedDate.value,  
            this.state.expiryDate.value,  
            this.state.uploadedDate.value,  
            this.state.documentFileUrl.value,  
            this.state.createdDate.value,  
            this.state.lastUpdatedDate.value,  
            this.state.clientId.value,  
            this.state.hasExtraData.value,  
            null, 
            null, 
          );

          result = await _clientDocumentRepository.create(newClientDocument);
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

  Stream<ClientDocumentState> onLoadClientDocumentIdForEdit(LoadClientDocumentByIdForEdit event) async* {
    yield this.state.copyWith(clientDocumentStatusUI: ClientDocumentStatusUI.loading);
    ClientDocument loadedClientDocument = await _clientDocumentRepository.getClientDocument(event.id);

    final documentName = DocumentNameInput.dirty(loadedClientDocument?.documentName != null ? loadedClientDocument.documentName: '');
    final documentNumber = DocumentNumberInput.dirty(loadedClientDocument?.documentNumber != null ? loadedClientDocument.documentNumber: '');
    final documentType = DocumentTypeInput.dirty(loadedClientDocument?.documentType != null ? loadedClientDocument.documentType: null);
    final documentStatus = DocumentStatusInput.dirty(loadedClientDocument?.documentStatus != null ? loadedClientDocument.documentStatus: null);
    final note = NoteInput.dirty(loadedClientDocument?.note != null ? loadedClientDocument.note: '');
    final issuedDate = IssuedDateInput.dirty(loadedClientDocument?.issuedDate != null ? loadedClientDocument.issuedDate: null);
    final expiryDate = ExpiryDateInput.dirty(loadedClientDocument?.expiryDate != null ? loadedClientDocument.expiryDate: null);
    final uploadedDate = UploadedDateInput.dirty(loadedClientDocument?.uploadedDate != null ? loadedClientDocument.uploadedDate: null);
    final documentFileUrl = DocumentFileUrlInput.dirty(loadedClientDocument?.documentFileUrl != null ? loadedClientDocument.documentFileUrl: '');
    final createdDate = CreatedDateInput.dirty(loadedClientDocument?.createdDate != null ? loadedClientDocument.createdDate: null);
    final lastUpdatedDate = LastUpdatedDateInput.dirty(loadedClientDocument?.lastUpdatedDate != null ? loadedClientDocument.lastUpdatedDate: null);
    final clientId = ClientIdInput.dirty(loadedClientDocument?.clientId != null ? loadedClientDocument.clientId: 0);
    final hasExtraData = HasExtraDataInput.dirty(loadedClientDocument?.hasExtraData != null ? loadedClientDocument.hasExtraData: false);

    yield this.state.copyWith(loadedClientDocument: loadedClientDocument, editMode: true,
      documentName: documentName,
      documentNumber: documentNumber,
      documentType: documentType,
      documentStatus: documentStatus,
      note: note,
      issuedDate: issuedDate,
      expiryDate: expiryDate,
      uploadedDate: uploadedDate,
      documentFileUrl: documentFileUrl,
      createdDate: createdDate,
      lastUpdatedDate: lastUpdatedDate,
      clientId: clientId,
      hasExtraData: hasExtraData,
    clientDocumentStatusUI: ClientDocumentStatusUI.done);

    documentNameController.text = loadedClientDocument.documentName;
    documentNumberController.text = loadedClientDocument.documentNumber;
    noteController.text = loadedClientDocument.note;
    issuedDateController.text = DateFormat.yMMMMd(S.current.locale).format(loadedClientDocument?.issuedDate);
    expiryDateController.text = DateFormat.yMMMMd(S.current.locale).format(loadedClientDocument?.expiryDate);
    uploadedDateController.text = DateFormat.yMMMMd(S.current.locale).format(loadedClientDocument?.uploadedDate);
    documentFileUrlController.text = loadedClientDocument.documentFileUrl;
    createdDateController.text = DateFormat.yMMMMd(S.current.locale).format(loadedClientDocument?.createdDate);
    lastUpdatedDateController.text = DateFormat.yMMMMd(S.current.locale).format(loadedClientDocument?.lastUpdatedDate);
    clientIdController.text = loadedClientDocument.clientId.toString();
  }

  Stream<ClientDocumentState> onDeleteClientDocumentId(DeleteClientDocumentById event) async* {
    try {
      await _clientDocumentRepository.delete(event.id);
      this.add(InitClientDocumentList());
      yield this.state.copyWith(deleteStatus: ClientDocumentDeleteStatus.ok);
    } catch (e) {
      yield this.state.copyWith(deleteStatus: ClientDocumentDeleteStatus.ko,
          generalNotificationKey: HttpUtils.errorServerKey);
    }
    yield this.state.copyWith(deleteStatus: ClientDocumentDeleteStatus.none);
  }

  Stream<ClientDocumentState> onLoadClientDocumentIdForView(LoadClientDocumentByIdForView event) async* {
    yield this.state.copyWith(clientDocumentStatusUI: ClientDocumentStatusUI.loading);
    try {
      ClientDocument loadedClientDocument = await _clientDocumentRepository.getClientDocument(event.id);
      yield this.state.copyWith(loadedClientDocument: loadedClientDocument, clientDocumentStatusUI: ClientDocumentStatusUI.done);
    } catch(e) {
      yield this.state.copyWith(loadedClientDocument: null, clientDocumentStatusUI: ClientDocumentStatusUI.error);
    }
  }


  Stream<ClientDocumentState> onDocumentNameChange(DocumentNameChanged event) async* {
    final documentName = DocumentNameInput.dirty(event.documentName);
    yield this.state.copyWith(
      documentName: documentName,
      formStatus: Formz.validate([
        documentName,
      this.state.documentNumber,
      this.state.documentType,
      this.state.documentStatus,
      this.state.note,
      this.state.issuedDate,
      this.state.expiryDate,
      this.state.uploadedDate,
      this.state.documentFileUrl,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<ClientDocumentState> onDocumentNumberChange(DocumentNumberChanged event) async* {
    final documentNumber = DocumentNumberInput.dirty(event.documentNumber);
    yield this.state.copyWith(
      documentNumber: documentNumber,
      formStatus: Formz.validate([
      this.state.documentName,
        documentNumber,
      this.state.documentType,
      this.state.documentStatus,
      this.state.note,
      this.state.issuedDate,
      this.state.expiryDate,
      this.state.uploadedDate,
      this.state.documentFileUrl,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<ClientDocumentState> onDocumentTypeChange(DocumentTypeChanged event) async* {
    final documentType = DocumentTypeInput.dirty(event.documentType);
    yield this.state.copyWith(
      documentType: documentType,
      formStatus: Formz.validate([
      this.state.documentName,
      this.state.documentNumber,
        documentType,
      this.state.documentStatus,
      this.state.note,
      this.state.issuedDate,
      this.state.expiryDate,
      this.state.uploadedDate,
      this.state.documentFileUrl,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<ClientDocumentState> onDocumentStatusChange(DocumentStatusChanged event) async* {
    final documentStatus = DocumentStatusInput.dirty(event.documentStatus);
    yield this.state.copyWith(
      documentStatus: documentStatus,
      formStatus: Formz.validate([
      this.state.documentName,
      this.state.documentNumber,
      this.state.documentType,
        documentStatus,
      this.state.note,
      this.state.issuedDate,
      this.state.expiryDate,
      this.state.uploadedDate,
      this.state.documentFileUrl,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<ClientDocumentState> onNoteChange(NoteChanged event) async* {
    final note = NoteInput.dirty(event.note);
    yield this.state.copyWith(
      note: note,
      formStatus: Formz.validate([
      this.state.documentName,
      this.state.documentNumber,
      this.state.documentType,
      this.state.documentStatus,
        note,
      this.state.issuedDate,
      this.state.expiryDate,
      this.state.uploadedDate,
      this.state.documentFileUrl,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<ClientDocumentState> onIssuedDateChange(IssuedDateChanged event) async* {
    final issuedDate = IssuedDateInput.dirty(event.issuedDate);
    yield this.state.copyWith(
      issuedDate: issuedDate,
      formStatus: Formz.validate([
      this.state.documentName,
      this.state.documentNumber,
      this.state.documentType,
      this.state.documentStatus,
      this.state.note,
        issuedDate,
      this.state.expiryDate,
      this.state.uploadedDate,
      this.state.documentFileUrl,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<ClientDocumentState> onExpiryDateChange(ExpiryDateChanged event) async* {
    final expiryDate = ExpiryDateInput.dirty(event.expiryDate);
    yield this.state.copyWith(
      expiryDate: expiryDate,
      formStatus: Formz.validate([
      this.state.documentName,
      this.state.documentNumber,
      this.state.documentType,
      this.state.documentStatus,
      this.state.note,
      this.state.issuedDate,
        expiryDate,
      this.state.uploadedDate,
      this.state.documentFileUrl,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<ClientDocumentState> onUploadedDateChange(UploadedDateChanged event) async* {
    final uploadedDate = UploadedDateInput.dirty(event.uploadedDate);
    yield this.state.copyWith(
      uploadedDate: uploadedDate,
      formStatus: Formz.validate([
      this.state.documentName,
      this.state.documentNumber,
      this.state.documentType,
      this.state.documentStatus,
      this.state.note,
      this.state.issuedDate,
      this.state.expiryDate,
        uploadedDate,
      this.state.documentFileUrl,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<ClientDocumentState> onDocumentFileUrlChange(DocumentFileUrlChanged event) async* {
    final documentFileUrl = DocumentFileUrlInput.dirty(event.documentFileUrl);
    yield this.state.copyWith(
      documentFileUrl: documentFileUrl,
      formStatus: Formz.validate([
      this.state.documentName,
      this.state.documentNumber,
      this.state.documentType,
      this.state.documentStatus,
      this.state.note,
      this.state.issuedDate,
      this.state.expiryDate,
      this.state.uploadedDate,
        documentFileUrl,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<ClientDocumentState> onCreatedDateChange(CreatedDateChanged event) async* {
    final createdDate = CreatedDateInput.dirty(event.createdDate);
    yield this.state.copyWith(
      createdDate: createdDate,
      formStatus: Formz.validate([
      this.state.documentName,
      this.state.documentNumber,
      this.state.documentType,
      this.state.documentStatus,
      this.state.note,
      this.state.issuedDate,
      this.state.expiryDate,
      this.state.uploadedDate,
      this.state.documentFileUrl,
        createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<ClientDocumentState> onLastUpdatedDateChange(LastUpdatedDateChanged event) async* {
    final lastUpdatedDate = LastUpdatedDateInput.dirty(event.lastUpdatedDate);
    yield this.state.copyWith(
      lastUpdatedDate: lastUpdatedDate,
      formStatus: Formz.validate([
      this.state.documentName,
      this.state.documentNumber,
      this.state.documentType,
      this.state.documentStatus,
      this.state.note,
      this.state.issuedDate,
      this.state.expiryDate,
      this.state.uploadedDate,
      this.state.documentFileUrl,
      this.state.createdDate,
        lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<ClientDocumentState> onClientIdChange(ClientIdChanged event) async* {
    final clientId = ClientIdInput.dirty(event.clientId);
    yield this.state.copyWith(
      clientId: clientId,
      formStatus: Formz.validate([
      this.state.documentName,
      this.state.documentNumber,
      this.state.documentType,
      this.state.documentStatus,
      this.state.note,
      this.state.issuedDate,
      this.state.expiryDate,
      this.state.uploadedDate,
      this.state.documentFileUrl,
      this.state.createdDate,
      this.state.lastUpdatedDate,
        clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<ClientDocumentState> onHasExtraDataChange(HasExtraDataChanged event) async* {
    final hasExtraData = HasExtraDataInput.dirty(event.hasExtraData);
    yield this.state.copyWith(
      hasExtraData: hasExtraData,
      formStatus: Formz.validate([
      this.state.documentName,
      this.state.documentNumber,
      this.state.documentType,
      this.state.documentStatus,
      this.state.note,
      this.state.issuedDate,
      this.state.expiryDate,
      this.state.uploadedDate,
      this.state.documentFileUrl,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
        hasExtraData,
      ]),
    );
  }

  @override
  Future<void> close() {
    documentNameController.dispose();
    documentNumberController.dispose();
    noteController.dispose();
    issuedDateController.dispose();
    expiryDateController.dispose();
    uploadedDateController.dispose();
    documentFileUrlController.dispose();
    createdDateController.dispose();
    lastUpdatedDateController.dispose();
    clientIdController.dispose();
    return super.close();
  }

}