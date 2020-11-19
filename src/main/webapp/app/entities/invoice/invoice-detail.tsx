import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './invoice.reducer';
import { IInvoice } from 'app/shared/model/invoice.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IInvoiceDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const InvoiceDetail = (props: IInvoiceDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { invoiceEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="carePlannerApp.invoice.detail.title">Invoice</Translate> [<b>{invoiceEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="totalAmount">
              <Translate contentKey="carePlannerApp.invoice.totalAmount">Total Amount</Translate>
            </span>
          </dt>
          <dd>{invoiceEntity.totalAmount}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="carePlannerApp.invoice.description">Description</Translate>
            </span>
          </dt>
          <dd>{invoiceEntity.description}</dd>
          <dt>
            <span id="invoiceNumber">
              <Translate contentKey="carePlannerApp.invoice.invoiceNumber">Invoice Number</Translate>
            </span>
          </dt>
          <dd>{invoiceEntity.invoiceNumber}</dd>
          <dt>
            <span id="generatedDate">
              <Translate contentKey="carePlannerApp.invoice.generatedDate">Generated Date</Translate>
            </span>
          </dt>
          <dd>
            {invoiceEntity.generatedDate ? <TextFormat value={invoiceEntity.generatedDate} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="dueDate">
              <Translate contentKey="carePlannerApp.invoice.dueDate">Due Date</Translate>
            </span>
          </dt>
          <dd>{invoiceEntity.dueDate ? <TextFormat value={invoiceEntity.dueDate} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="paymentDate">
              <Translate contentKey="carePlannerApp.invoice.paymentDate">Payment Date</Translate>
            </span>
          </dt>
          <dd>
            {invoiceEntity.paymentDate ? <TextFormat value={invoiceEntity.paymentDate} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="invoiceStatus">
              <Translate contentKey="carePlannerApp.invoice.invoiceStatus">Invoice Status</Translate>
            </span>
          </dt>
          <dd>{invoiceEntity.invoiceStatus}</dd>
          <dt>
            <span id="tax">
              <Translate contentKey="carePlannerApp.invoice.tax">Tax</Translate>
            </span>
          </dt>
          <dd>{invoiceEntity.tax}</dd>
          <dt>
            <span id="attribute1">
              <Translate contentKey="carePlannerApp.invoice.attribute1">Attribute 1</Translate>
            </span>
          </dt>
          <dd>{invoiceEntity.attribute1}</dd>
          <dt>
            <span id="attribute2">
              <Translate contentKey="carePlannerApp.invoice.attribute2">Attribute 2</Translate>
            </span>
          </dt>
          <dd>{invoiceEntity.attribute2}</dd>
          <dt>
            <span id="attribute3">
              <Translate contentKey="carePlannerApp.invoice.attribute3">Attribute 3</Translate>
            </span>
          </dt>
          <dd>{invoiceEntity.attribute3}</dd>
          <dt>
            <span id="attribute4">
              <Translate contentKey="carePlannerApp.invoice.attribute4">Attribute 4</Translate>
            </span>
          </dt>
          <dd>{invoiceEntity.attribute4}</dd>
          <dt>
            <span id="attribute5">
              <Translate contentKey="carePlannerApp.invoice.attribute5">Attribute 5</Translate>
            </span>
          </dt>
          <dd>{invoiceEntity.attribute5}</dd>
          <dt>
            <span id="attribute6">
              <Translate contentKey="carePlannerApp.invoice.attribute6">Attribute 6</Translate>
            </span>
          </dt>
          <dd>{invoiceEntity.attribute6}</dd>
          <dt>
            <span id="attribute7">
              <Translate contentKey="carePlannerApp.invoice.attribute7">Attribute 7</Translate>
            </span>
          </dt>
          <dd>{invoiceEntity.attribute7}</dd>
          <dt>
            <span id="lastUpdatedDate">
              <Translate contentKey="carePlannerApp.invoice.lastUpdatedDate">Last Updated Date</Translate>
            </span>
          </dt>
          <dd>
            {invoiceEntity.lastUpdatedDate ? (
              <TextFormat value={invoiceEntity.lastUpdatedDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="tenantId">
              <Translate contentKey="carePlannerApp.invoice.tenantId">Tenant Id</Translate>
            </span>
          </dt>
          <dd>{invoiceEntity.tenantId}</dd>
          <dt>
            <Translate contentKey="carePlannerApp.invoice.serviceOrder">Service Order</Translate>
          </dt>
          <dd>{invoiceEntity.serviceOrderTitle ? invoiceEntity.serviceOrderTitle : ''}</dd>
          <dt>
            <Translate contentKey="carePlannerApp.invoice.serviceUser">Service User</Translate>
          </dt>
          <dd>{invoiceEntity.serviceUserServiceUserCode ? invoiceEntity.serviceUserServiceUserCode : ''}</dd>
        </dl>
        <Button tag={Link} to="/invoice" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/invoice/${invoiceEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ invoice }: IRootState) => ({
  invoiceEntity: invoice.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(InvoiceDetail);
