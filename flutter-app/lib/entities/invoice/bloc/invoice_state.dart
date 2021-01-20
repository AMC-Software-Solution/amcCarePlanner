part of 'invoice_bloc.dart';

enum InvoiceStatusUI {init, loading, error, done}
enum InvoiceDeleteStatus {ok, ko, none}

class InvoiceState extends Equatable {
  final List<Invoice> invoices;
  final Invoice loadedInvoice;
  final bool editMode;
  final InvoiceDeleteStatus deleteStatus;
  final InvoiceStatusUI invoiceStatusUI;

  final FormzStatus formStatus;
  final String generalNotificationKey;

  final TotalAmountInput totalAmount;
  final DescriptionInput description;
  final InvoiceNumberInput invoiceNumber;
  final GeneratedDateInput generatedDate;
  final DueDateInput dueDate;
  final PaymentDateInput paymentDate;
  final InvoiceStatusInput invoiceStatus;
  final TaxInput tax;
  final Attribute1Input attribute1;
  final Attribute2Input attribute2;
  final Attribute3Input attribute3;
  final Attribute4Input attribute4;
  final Attribute5Input attribute5;
  final Attribute6Input attribute6;
  final Attribute7Input attribute7;
  final CreatedDateInput createdDate;
  final LastUpdatedDateInput lastUpdatedDate;
  final ClientIdInput clientId;
  final HasExtraDataInput hasExtraData;

  
  InvoiceState(
GeneratedDateInput generatedDate,DueDateInput dueDate,PaymentDateInput paymentDate,CreatedDateInput createdDate,LastUpdatedDateInput lastUpdatedDate,{
    this.invoices = const [],
    this.invoiceStatusUI = InvoiceStatusUI.init,
    this.loadedInvoice = const Invoice(0,'','','',null,null,null,null,'','','','','','','','',null,null,0,false,null,null,),
    this.editMode = false,
    this.formStatus = FormzStatus.pure,
    this.generalNotificationKey = '',
    this.deleteStatus = InvoiceDeleteStatus.none,
    this.totalAmount = const TotalAmountInput.pure(),
    this.description = const DescriptionInput.pure(),
    this.invoiceNumber = const InvoiceNumberInput.pure(),
    this.invoiceStatus = const InvoiceStatusInput.pure(),
    this.tax = const TaxInput.pure(),
    this.attribute1 = const Attribute1Input.pure(),
    this.attribute2 = const Attribute2Input.pure(),
    this.attribute3 = const Attribute3Input.pure(),
    this.attribute4 = const Attribute4Input.pure(),
    this.attribute5 = const Attribute5Input.pure(),
    this.attribute6 = const Attribute6Input.pure(),
    this.attribute7 = const Attribute7Input.pure(),
    this.clientId = const ClientIdInput.pure(),
    this.hasExtraData = const HasExtraDataInput.pure(),
  }):this.generatedDate = generatedDate ?? GeneratedDateInput.pure(),
this.dueDate = dueDate ?? DueDateInput.pure(),
this.paymentDate = paymentDate ?? PaymentDateInput.pure(),
this.createdDate = createdDate ?? CreatedDateInput.pure(),
this.lastUpdatedDate = lastUpdatedDate ?? LastUpdatedDateInput.pure()
;

  InvoiceState copyWith({
    List<Invoice> invoices,
    InvoiceStatusUI invoiceStatusUI,
    bool editMode,
    InvoiceDeleteStatus deleteStatus,
    Invoice loadedInvoice,
    FormzStatus formStatus,
    String generalNotificationKey,
    TotalAmountInput totalAmount,
    DescriptionInput description,
    InvoiceNumberInput invoiceNumber,
    GeneratedDateInput generatedDate,
    DueDateInput dueDate,
    PaymentDateInput paymentDate,
    InvoiceStatusInput invoiceStatus,
    TaxInput tax,
    Attribute1Input attribute1,
    Attribute2Input attribute2,
    Attribute3Input attribute3,
    Attribute4Input attribute4,
    Attribute5Input attribute5,
    Attribute6Input attribute6,
    Attribute7Input attribute7,
    CreatedDateInput createdDate,
    LastUpdatedDateInput lastUpdatedDate,
    ClientIdInput clientId,
    HasExtraDataInput hasExtraData,
  }) {
    return InvoiceState(
        generatedDate,
        dueDate,
        paymentDate,
        createdDate,
        lastUpdatedDate,
      invoices: invoices ?? this.invoices,
      invoiceStatusUI: invoiceStatusUI ?? this.invoiceStatusUI,
      loadedInvoice: loadedInvoice ?? this.loadedInvoice,
      editMode: editMode ?? this.editMode,
      formStatus: formStatus ?? this.formStatus,
      generalNotificationKey: generalNotificationKey ?? this.generalNotificationKey,
      deleteStatus: deleteStatus ?? this.deleteStatus,
      totalAmount: totalAmount ?? this.totalAmount,
      description: description ?? this.description,
      invoiceNumber: invoiceNumber ?? this.invoiceNumber,
      invoiceStatus: invoiceStatus ?? this.invoiceStatus,
      tax: tax ?? this.tax,
      attribute1: attribute1 ?? this.attribute1,
      attribute2: attribute2 ?? this.attribute2,
      attribute3: attribute3 ?? this.attribute3,
      attribute4: attribute4 ?? this.attribute4,
      attribute5: attribute5 ?? this.attribute5,
      attribute6: attribute6 ?? this.attribute6,
      attribute7: attribute7 ?? this.attribute7,
      clientId: clientId ?? this.clientId,
      hasExtraData: hasExtraData ?? this.hasExtraData,
    );
  }

  @override
  List<Object> get props => [invoices, invoiceStatusUI,
     loadedInvoice, editMode, deleteStatus, formStatus, generalNotificationKey, 
totalAmount,description,invoiceNumber,generatedDate,dueDate,paymentDate,invoiceStatus,tax,attribute1,attribute2,attribute3,attribute4,attribute5,attribute6,attribute7,createdDate,lastUpdatedDate,clientId,hasExtraData,];

  @override
  bool get stringify => true;
}
