import 'dart:async';
import 'package:bloc/bloc.dart';
import 'package:flutter/foundation.dart';
import 'package:equatable/equatable.dart';
import 'package:flutter/widgets.dart';
import 'package:formz/formz.dart';

import 'package:amcCarePlanner/entities/invoice/invoice_model.dart';
import 'package:amcCarePlanner/entities/invoice/invoice_repository.dart';
import 'package:amcCarePlanner/entities/invoice/bloc/invoice_form_model.dart';
import 'package:amcCarePlanner/generated/l10n.dart';
import 'package:amcCarePlanner/shared/repository/http_utils.dart';
import 'package:intl/intl.dart';

part 'invoice_events.dart';
part 'invoice_state.dart';

class InvoiceBloc extends Bloc<InvoiceEvent, InvoiceState> {
  final InvoiceRepository _invoiceRepository;

  final totalAmountController = TextEditingController();
  final descriptionController = TextEditingController();
  final invoiceNumberController = TextEditingController();
  final generatedDateController = TextEditingController();
  final dueDateController = TextEditingController();
  final paymentDateController = TextEditingController();
  final taxController = TextEditingController();
  final attribute1Controller = TextEditingController();
  final attribute2Controller = TextEditingController();
  final attribute3Controller = TextEditingController();
  final attribute4Controller = TextEditingController();
  final attribute5Controller = TextEditingController();
  final attribute6Controller = TextEditingController();
  final attribute7Controller = TextEditingController();
  final createdDateController = TextEditingController();
  final lastUpdatedDateController = TextEditingController();
  final clientIdController = TextEditingController();

  InvoiceBloc({@required InvoiceRepository invoiceRepository}) : assert(invoiceRepository != null),
        _invoiceRepository = invoiceRepository, 
  super(InvoiceState(null,null,null,null,null,));

  @override
  void onTransition(Transition<InvoiceEvent, InvoiceState> transition) {
    super.onTransition(transition);
  }

  @override
  Stream<InvoiceState> mapEventToState(InvoiceEvent event) async* {
    if (event is InitInvoiceList) {
      yield* onInitList(event);
    } else if (event is InvoiceFormSubmitted) {
      yield* onSubmit();
    } else if (event is LoadInvoiceByIdForEdit) {
      yield* onLoadInvoiceIdForEdit(event);
    } else if (event is DeleteInvoiceById) {
      yield* onDeleteInvoiceId(event);
    } else if (event is LoadInvoiceByIdForView) {
      yield* onLoadInvoiceIdForView(event);
    }else if (event is TotalAmountChanged){
      yield* onTotalAmountChange(event);
    }else if (event is DescriptionChanged){
      yield* onDescriptionChange(event);
    }else if (event is InvoiceNumberChanged){
      yield* onInvoiceNumberChange(event);
    }else if (event is GeneratedDateChanged){
      yield* onGeneratedDateChange(event);
    }else if (event is DueDateChanged){
      yield* onDueDateChange(event);
    }else if (event is PaymentDateChanged){
      yield* onPaymentDateChange(event);
    }else if (event is InvoiceStatusChanged){
      yield* onInvoiceStatusChange(event);
    }else if (event is TaxChanged){
      yield* onTaxChange(event);
    }else if (event is Attribute1Changed){
      yield* onAttribute1Change(event);
    }else if (event is Attribute2Changed){
      yield* onAttribute2Change(event);
    }else if (event is Attribute3Changed){
      yield* onAttribute3Change(event);
    }else if (event is Attribute4Changed){
      yield* onAttribute4Change(event);
    }else if (event is Attribute5Changed){
      yield* onAttribute5Change(event);
    }else if (event is Attribute6Changed){
      yield* onAttribute6Change(event);
    }else if (event is Attribute7Changed){
      yield* onAttribute7Change(event);
    }else if (event is CreatedDateChanged){
      yield* onCreatedDateChange(event);
    }else if (event is LastUpdatedDateChanged){
      yield* onLastUpdatedDateChange(event);
    }else if (event is ClientIdChanged){
      yield* onClientIdChange(event);
    }else if (event is HasExtraDataChanged){
      yield* onHasExtraDataChange(event);
    }  }

  Stream<InvoiceState> onInitList(InitInvoiceList event) async* {
    yield this.state.copyWith(invoiceStatusUI: InvoiceStatusUI.loading);
    List<Invoice> invoices = await _invoiceRepository.getAllInvoices();
    yield this.state.copyWith(invoices: invoices, invoiceStatusUI: InvoiceStatusUI.done);
  }

  Stream<InvoiceState> onSubmit() async* {
    if (this.state.formStatus.isValidated) {
      yield this.state.copyWith(formStatus: FormzStatus.submissionInProgress);
      try {
        Invoice result;
        if(this.state.editMode) {
          Invoice newInvoice = Invoice(state.loadedInvoice.id,
            this.state.totalAmount.value,  
            this.state.description.value,  
            this.state.invoiceNumber.value,  
            this.state.generatedDate.value,  
            this.state.dueDate.value,  
            this.state.paymentDate.value,  
            this.state.invoiceStatus.value,  
            this.state.tax.value,  
            this.state.attribute1.value,  
            this.state.attribute2.value,  
            this.state.attribute3.value,  
            this.state.attribute4.value,  
            this.state.attribute5.value,  
            this.state.attribute6.value,  
            this.state.attribute7.value,  
            this.state.createdDate.value,  
            this.state.lastUpdatedDate.value,  
            this.state.clientId.value,  
            this.state.hasExtraData.value,  
            null, 
            null, 
          );

          result = await _invoiceRepository.update(newInvoice);
        } else {
          Invoice newInvoice = Invoice(null,
            this.state.totalAmount.value,  
            this.state.description.value,  
            this.state.invoiceNumber.value,  
            this.state.generatedDate.value,  
            this.state.dueDate.value,  
            this.state.paymentDate.value,  
            this.state.invoiceStatus.value,  
            this.state.tax.value,  
            this.state.attribute1.value,  
            this.state.attribute2.value,  
            this.state.attribute3.value,  
            this.state.attribute4.value,  
            this.state.attribute5.value,  
            this.state.attribute6.value,  
            this.state.attribute7.value,  
            this.state.createdDate.value,  
            this.state.lastUpdatedDate.value,  
            this.state.clientId.value,  
            this.state.hasExtraData.value,  
            null, 
            null, 
          );

          result = await _invoiceRepository.create(newInvoice);
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

  Stream<InvoiceState> onLoadInvoiceIdForEdit(LoadInvoiceByIdForEdit event) async* {
    yield this.state.copyWith(invoiceStatusUI: InvoiceStatusUI.loading);
    Invoice loadedInvoice = await _invoiceRepository.getInvoice(event.id);

    final totalAmount = TotalAmountInput.dirty(loadedInvoice?.totalAmount != null ? loadedInvoice.totalAmount: '');
    final description = DescriptionInput.dirty(loadedInvoice?.description != null ? loadedInvoice.description: '');
    final invoiceNumber = InvoiceNumberInput.dirty(loadedInvoice?.invoiceNumber != null ? loadedInvoice.invoiceNumber: '');
    final generatedDate = GeneratedDateInput.dirty(loadedInvoice?.generatedDate != null ? loadedInvoice.generatedDate: null);
    final dueDate = DueDateInput.dirty(loadedInvoice?.dueDate != null ? loadedInvoice.dueDate: null);
    final paymentDate = PaymentDateInput.dirty(loadedInvoice?.paymentDate != null ? loadedInvoice.paymentDate: null);
    final invoiceStatus = InvoiceStatusInput.dirty(loadedInvoice?.invoiceStatus != null ? loadedInvoice.invoiceStatus: null);
    final tax = TaxInput.dirty(loadedInvoice?.tax != null ? loadedInvoice.tax: '');
    final attribute1 = Attribute1Input.dirty(loadedInvoice?.attribute1 != null ? loadedInvoice.attribute1: '');
    final attribute2 = Attribute2Input.dirty(loadedInvoice?.attribute2 != null ? loadedInvoice.attribute2: '');
    final attribute3 = Attribute3Input.dirty(loadedInvoice?.attribute3 != null ? loadedInvoice.attribute3: '');
    final attribute4 = Attribute4Input.dirty(loadedInvoice?.attribute4 != null ? loadedInvoice.attribute4: '');
    final attribute5 = Attribute5Input.dirty(loadedInvoice?.attribute5 != null ? loadedInvoice.attribute5: '');
    final attribute6 = Attribute6Input.dirty(loadedInvoice?.attribute6 != null ? loadedInvoice.attribute6: '');
    final attribute7 = Attribute7Input.dirty(loadedInvoice?.attribute7 != null ? loadedInvoice.attribute7: '');
    final createdDate = CreatedDateInput.dirty(loadedInvoice?.createdDate != null ? loadedInvoice.createdDate: null);
    final lastUpdatedDate = LastUpdatedDateInput.dirty(loadedInvoice?.lastUpdatedDate != null ? loadedInvoice.lastUpdatedDate: null);
    final clientId = ClientIdInput.dirty(loadedInvoice?.clientId != null ? loadedInvoice.clientId: 0);
    final hasExtraData = HasExtraDataInput.dirty(loadedInvoice?.hasExtraData != null ? loadedInvoice.hasExtraData: false);

    yield this.state.copyWith(loadedInvoice: loadedInvoice, editMode: true,
      totalAmount: totalAmount,
      description: description,
      invoiceNumber: invoiceNumber,
      generatedDate: generatedDate,
      dueDate: dueDate,
      paymentDate: paymentDate,
      invoiceStatus: invoiceStatus,
      tax: tax,
      attribute1: attribute1,
      attribute2: attribute2,
      attribute3: attribute3,
      attribute4: attribute4,
      attribute5: attribute5,
      attribute6: attribute6,
      attribute7: attribute7,
      createdDate: createdDate,
      lastUpdatedDate: lastUpdatedDate,
      clientId: clientId,
      hasExtraData: hasExtraData,
    invoiceStatusUI: InvoiceStatusUI.done);

    totalAmountController.text = loadedInvoice.totalAmount;
    descriptionController.text = loadedInvoice.description;
    invoiceNumberController.text = loadedInvoice.invoiceNumber;
    generatedDateController.text = DateFormat.yMMMMd(S.current.locale).format(loadedInvoice?.generatedDate);
    dueDateController.text = DateFormat.yMMMMd(S.current.locale).format(loadedInvoice?.dueDate);
    paymentDateController.text = DateFormat.yMMMMd(S.current.locale).format(loadedInvoice?.paymentDate);
    taxController.text = loadedInvoice.tax;
    attribute1Controller.text = loadedInvoice.attribute1;
    attribute2Controller.text = loadedInvoice.attribute2;
    attribute3Controller.text = loadedInvoice.attribute3;
    attribute4Controller.text = loadedInvoice.attribute4;
    attribute5Controller.text = loadedInvoice.attribute5;
    attribute6Controller.text = loadedInvoice.attribute6;
    attribute7Controller.text = loadedInvoice.attribute7;
    createdDateController.text = DateFormat.yMMMMd(S.current.locale).format(loadedInvoice?.createdDate);
    lastUpdatedDateController.text = DateFormat.yMMMMd(S.current.locale).format(loadedInvoice?.lastUpdatedDate);
    clientIdController.text = loadedInvoice.clientId.toString();
  }

  Stream<InvoiceState> onDeleteInvoiceId(DeleteInvoiceById event) async* {
    try {
      await _invoiceRepository.delete(event.id);
      this.add(InitInvoiceList());
      yield this.state.copyWith(deleteStatus: InvoiceDeleteStatus.ok);
    } catch (e) {
      yield this.state.copyWith(deleteStatus: InvoiceDeleteStatus.ko,
          generalNotificationKey: HttpUtils.errorServerKey);
    }
    yield this.state.copyWith(deleteStatus: InvoiceDeleteStatus.none);
  }

  Stream<InvoiceState> onLoadInvoiceIdForView(LoadInvoiceByIdForView event) async* {
    yield this.state.copyWith(invoiceStatusUI: InvoiceStatusUI.loading);
    try {
      Invoice loadedInvoice = await _invoiceRepository.getInvoice(event.id);
      yield this.state.copyWith(loadedInvoice: loadedInvoice, invoiceStatusUI: InvoiceStatusUI.done);
    } catch(e) {
      yield this.state.copyWith(loadedInvoice: null, invoiceStatusUI: InvoiceStatusUI.error);
    }
  }


  Stream<InvoiceState> onTotalAmountChange(TotalAmountChanged event) async* {
    final totalAmount = TotalAmountInput.dirty(event.totalAmount);
    yield this.state.copyWith(
      totalAmount: totalAmount,
      formStatus: Formz.validate([
        totalAmount,
      this.state.description,
      this.state.invoiceNumber,
      this.state.generatedDate,
      this.state.dueDate,
      this.state.paymentDate,
      this.state.invoiceStatus,
      this.state.tax,
      this.state.attribute1,
      this.state.attribute2,
      this.state.attribute3,
      this.state.attribute4,
      this.state.attribute5,
      this.state.attribute6,
      this.state.attribute7,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<InvoiceState> onDescriptionChange(DescriptionChanged event) async* {
    final description = DescriptionInput.dirty(event.description);
    yield this.state.copyWith(
      description: description,
      formStatus: Formz.validate([
      this.state.totalAmount,
        description,
      this.state.invoiceNumber,
      this.state.generatedDate,
      this.state.dueDate,
      this.state.paymentDate,
      this.state.invoiceStatus,
      this.state.tax,
      this.state.attribute1,
      this.state.attribute2,
      this.state.attribute3,
      this.state.attribute4,
      this.state.attribute5,
      this.state.attribute6,
      this.state.attribute7,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<InvoiceState> onInvoiceNumberChange(InvoiceNumberChanged event) async* {
    final invoiceNumber = InvoiceNumberInput.dirty(event.invoiceNumber);
    yield this.state.copyWith(
      invoiceNumber: invoiceNumber,
      formStatus: Formz.validate([
      this.state.totalAmount,
      this.state.description,
        invoiceNumber,
      this.state.generatedDate,
      this.state.dueDate,
      this.state.paymentDate,
      this.state.invoiceStatus,
      this.state.tax,
      this.state.attribute1,
      this.state.attribute2,
      this.state.attribute3,
      this.state.attribute4,
      this.state.attribute5,
      this.state.attribute6,
      this.state.attribute7,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<InvoiceState> onGeneratedDateChange(GeneratedDateChanged event) async* {
    final generatedDate = GeneratedDateInput.dirty(event.generatedDate);
    yield this.state.copyWith(
      generatedDate: generatedDate,
      formStatus: Formz.validate([
      this.state.totalAmount,
      this.state.description,
      this.state.invoiceNumber,
        generatedDate,
      this.state.dueDate,
      this.state.paymentDate,
      this.state.invoiceStatus,
      this.state.tax,
      this.state.attribute1,
      this.state.attribute2,
      this.state.attribute3,
      this.state.attribute4,
      this.state.attribute5,
      this.state.attribute6,
      this.state.attribute7,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<InvoiceState> onDueDateChange(DueDateChanged event) async* {
    final dueDate = DueDateInput.dirty(event.dueDate);
    yield this.state.copyWith(
      dueDate: dueDate,
      formStatus: Formz.validate([
      this.state.totalAmount,
      this.state.description,
      this.state.invoiceNumber,
      this.state.generatedDate,
        dueDate,
      this.state.paymentDate,
      this.state.invoiceStatus,
      this.state.tax,
      this.state.attribute1,
      this.state.attribute2,
      this.state.attribute3,
      this.state.attribute4,
      this.state.attribute5,
      this.state.attribute6,
      this.state.attribute7,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<InvoiceState> onPaymentDateChange(PaymentDateChanged event) async* {
    final paymentDate = PaymentDateInput.dirty(event.paymentDate);
    yield this.state.copyWith(
      paymentDate: paymentDate,
      formStatus: Formz.validate([
      this.state.totalAmount,
      this.state.description,
      this.state.invoiceNumber,
      this.state.generatedDate,
      this.state.dueDate,
        paymentDate,
      this.state.invoiceStatus,
      this.state.tax,
      this.state.attribute1,
      this.state.attribute2,
      this.state.attribute3,
      this.state.attribute4,
      this.state.attribute5,
      this.state.attribute6,
      this.state.attribute7,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<InvoiceState> onInvoiceStatusChange(InvoiceStatusChanged event) async* {
    final invoiceStatus = InvoiceStatusInput.dirty(event.invoiceStatus);
    yield this.state.copyWith(
      invoiceStatus: invoiceStatus,
      formStatus: Formz.validate([
      this.state.totalAmount,
      this.state.description,
      this.state.invoiceNumber,
      this.state.generatedDate,
      this.state.dueDate,
      this.state.paymentDate,
        invoiceStatus,
      this.state.tax,
      this.state.attribute1,
      this.state.attribute2,
      this.state.attribute3,
      this.state.attribute4,
      this.state.attribute5,
      this.state.attribute6,
      this.state.attribute7,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<InvoiceState> onTaxChange(TaxChanged event) async* {
    final tax = TaxInput.dirty(event.tax);
    yield this.state.copyWith(
      tax: tax,
      formStatus: Formz.validate([
      this.state.totalAmount,
      this.state.description,
      this.state.invoiceNumber,
      this.state.generatedDate,
      this.state.dueDate,
      this.state.paymentDate,
      this.state.invoiceStatus,
        tax,
      this.state.attribute1,
      this.state.attribute2,
      this.state.attribute3,
      this.state.attribute4,
      this.state.attribute5,
      this.state.attribute6,
      this.state.attribute7,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<InvoiceState> onAttribute1Change(Attribute1Changed event) async* {
    final attribute1 = Attribute1Input.dirty(event.attribute1);
    yield this.state.copyWith(
      attribute1: attribute1,
      formStatus: Formz.validate([
      this.state.totalAmount,
      this.state.description,
      this.state.invoiceNumber,
      this.state.generatedDate,
      this.state.dueDate,
      this.state.paymentDate,
      this.state.invoiceStatus,
      this.state.tax,
        attribute1,
      this.state.attribute2,
      this.state.attribute3,
      this.state.attribute4,
      this.state.attribute5,
      this.state.attribute6,
      this.state.attribute7,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<InvoiceState> onAttribute2Change(Attribute2Changed event) async* {
    final attribute2 = Attribute2Input.dirty(event.attribute2);
    yield this.state.copyWith(
      attribute2: attribute2,
      formStatus: Formz.validate([
      this.state.totalAmount,
      this.state.description,
      this.state.invoiceNumber,
      this.state.generatedDate,
      this.state.dueDate,
      this.state.paymentDate,
      this.state.invoiceStatus,
      this.state.tax,
      this.state.attribute1,
        attribute2,
      this.state.attribute3,
      this.state.attribute4,
      this.state.attribute5,
      this.state.attribute6,
      this.state.attribute7,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<InvoiceState> onAttribute3Change(Attribute3Changed event) async* {
    final attribute3 = Attribute3Input.dirty(event.attribute3);
    yield this.state.copyWith(
      attribute3: attribute3,
      formStatus: Formz.validate([
      this.state.totalAmount,
      this.state.description,
      this.state.invoiceNumber,
      this.state.generatedDate,
      this.state.dueDate,
      this.state.paymentDate,
      this.state.invoiceStatus,
      this.state.tax,
      this.state.attribute1,
      this.state.attribute2,
        attribute3,
      this.state.attribute4,
      this.state.attribute5,
      this.state.attribute6,
      this.state.attribute7,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<InvoiceState> onAttribute4Change(Attribute4Changed event) async* {
    final attribute4 = Attribute4Input.dirty(event.attribute4);
    yield this.state.copyWith(
      attribute4: attribute4,
      formStatus: Formz.validate([
      this.state.totalAmount,
      this.state.description,
      this.state.invoiceNumber,
      this.state.generatedDate,
      this.state.dueDate,
      this.state.paymentDate,
      this.state.invoiceStatus,
      this.state.tax,
      this.state.attribute1,
      this.state.attribute2,
      this.state.attribute3,
        attribute4,
      this.state.attribute5,
      this.state.attribute6,
      this.state.attribute7,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<InvoiceState> onAttribute5Change(Attribute5Changed event) async* {
    final attribute5 = Attribute5Input.dirty(event.attribute5);
    yield this.state.copyWith(
      attribute5: attribute5,
      formStatus: Formz.validate([
      this.state.totalAmount,
      this.state.description,
      this.state.invoiceNumber,
      this.state.generatedDate,
      this.state.dueDate,
      this.state.paymentDate,
      this.state.invoiceStatus,
      this.state.tax,
      this.state.attribute1,
      this.state.attribute2,
      this.state.attribute3,
      this.state.attribute4,
        attribute5,
      this.state.attribute6,
      this.state.attribute7,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<InvoiceState> onAttribute6Change(Attribute6Changed event) async* {
    final attribute6 = Attribute6Input.dirty(event.attribute6);
    yield this.state.copyWith(
      attribute6: attribute6,
      formStatus: Formz.validate([
      this.state.totalAmount,
      this.state.description,
      this.state.invoiceNumber,
      this.state.generatedDate,
      this.state.dueDate,
      this.state.paymentDate,
      this.state.invoiceStatus,
      this.state.tax,
      this.state.attribute1,
      this.state.attribute2,
      this.state.attribute3,
      this.state.attribute4,
      this.state.attribute5,
        attribute6,
      this.state.attribute7,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<InvoiceState> onAttribute7Change(Attribute7Changed event) async* {
    final attribute7 = Attribute7Input.dirty(event.attribute7);
    yield this.state.copyWith(
      attribute7: attribute7,
      formStatus: Formz.validate([
      this.state.totalAmount,
      this.state.description,
      this.state.invoiceNumber,
      this.state.generatedDate,
      this.state.dueDate,
      this.state.paymentDate,
      this.state.invoiceStatus,
      this.state.tax,
      this.state.attribute1,
      this.state.attribute2,
      this.state.attribute3,
      this.state.attribute4,
      this.state.attribute5,
      this.state.attribute6,
        attribute7,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<InvoiceState> onCreatedDateChange(CreatedDateChanged event) async* {
    final createdDate = CreatedDateInput.dirty(event.createdDate);
    yield this.state.copyWith(
      createdDate: createdDate,
      formStatus: Formz.validate([
      this.state.totalAmount,
      this.state.description,
      this.state.invoiceNumber,
      this.state.generatedDate,
      this.state.dueDate,
      this.state.paymentDate,
      this.state.invoiceStatus,
      this.state.tax,
      this.state.attribute1,
      this.state.attribute2,
      this.state.attribute3,
      this.state.attribute4,
      this.state.attribute5,
      this.state.attribute6,
      this.state.attribute7,
        createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<InvoiceState> onLastUpdatedDateChange(LastUpdatedDateChanged event) async* {
    final lastUpdatedDate = LastUpdatedDateInput.dirty(event.lastUpdatedDate);
    yield this.state.copyWith(
      lastUpdatedDate: lastUpdatedDate,
      formStatus: Formz.validate([
      this.state.totalAmount,
      this.state.description,
      this.state.invoiceNumber,
      this.state.generatedDate,
      this.state.dueDate,
      this.state.paymentDate,
      this.state.invoiceStatus,
      this.state.tax,
      this.state.attribute1,
      this.state.attribute2,
      this.state.attribute3,
      this.state.attribute4,
      this.state.attribute5,
      this.state.attribute6,
      this.state.attribute7,
      this.state.createdDate,
        lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<InvoiceState> onClientIdChange(ClientIdChanged event) async* {
    final clientId = ClientIdInput.dirty(event.clientId);
    yield this.state.copyWith(
      clientId: clientId,
      formStatus: Formz.validate([
      this.state.totalAmount,
      this.state.description,
      this.state.invoiceNumber,
      this.state.generatedDate,
      this.state.dueDate,
      this.state.paymentDate,
      this.state.invoiceStatus,
      this.state.tax,
      this.state.attribute1,
      this.state.attribute2,
      this.state.attribute3,
      this.state.attribute4,
      this.state.attribute5,
      this.state.attribute6,
      this.state.attribute7,
      this.state.createdDate,
      this.state.lastUpdatedDate,
        clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<InvoiceState> onHasExtraDataChange(HasExtraDataChanged event) async* {
    final hasExtraData = HasExtraDataInput.dirty(event.hasExtraData);
    yield this.state.copyWith(
      hasExtraData: hasExtraData,
      formStatus: Formz.validate([
      this.state.totalAmount,
      this.state.description,
      this.state.invoiceNumber,
      this.state.generatedDate,
      this.state.dueDate,
      this.state.paymentDate,
      this.state.invoiceStatus,
      this.state.tax,
      this.state.attribute1,
      this.state.attribute2,
      this.state.attribute3,
      this.state.attribute4,
      this.state.attribute5,
      this.state.attribute6,
      this.state.attribute7,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
        hasExtraData,
      ]),
    );
  }

  @override
  Future<void> close() {
    totalAmountController.dispose();
    descriptionController.dispose();
    invoiceNumberController.dispose();
    generatedDateController.dispose();
    dueDateController.dispose();
    paymentDateController.dispose();
    taxController.dispose();
    attribute1Controller.dispose();
    attribute2Controller.dispose();
    attribute3Controller.dispose();
    attribute4Controller.dispose();
    attribute5Controller.dispose();
    attribute6Controller.dispose();
    attribute7Controller.dispose();
    createdDateController.dispose();
    lastUpdatedDateController.dispose();
    clientIdController.dispose();
    return super.close();
  }

}