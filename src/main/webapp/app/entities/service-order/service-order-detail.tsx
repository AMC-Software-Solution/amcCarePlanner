import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './service-order.reducer';
import { IServiceOrder } from 'app/shared/model/service-order.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IServiceOrderDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ServiceOrderDetail = (props: IServiceOrderDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { serviceOrderEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="carePlannerApp.serviceOrder.detail.title">ServiceOrder</Translate> [<b>{serviceOrderEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="title">
              <Translate contentKey="carePlannerApp.serviceOrder.title">Title</Translate>
            </span>
          </dt>
          <dd>{serviceOrderEntity.title}</dd>
          <dt>
            <span id="serviceDescription">
              <Translate contentKey="carePlannerApp.serviceOrder.serviceDescription">Service Description</Translate>
            </span>
          </dt>
          <dd>{serviceOrderEntity.serviceDescription}</dd>
          <dt>
            <span id="serviceRate">
              <Translate contentKey="carePlannerApp.serviceOrder.serviceRate">Service Rate</Translate>
            </span>
          </dt>
          <dd>{serviceOrderEntity.serviceRate}</dd>
          <dt>
            <span id="tenantId">
              <Translate contentKey="carePlannerApp.serviceOrder.tenantId">Tenant Id</Translate>
            </span>
          </dt>
          <dd>{serviceOrderEntity.tenantId}</dd>
          <dt>
            <span id="lastUpdatedDate">
              <Translate contentKey="carePlannerApp.serviceOrder.lastUpdatedDate">Last Updated Date</Translate>
            </span>
          </dt>
          <dd>
            {serviceOrderEntity.lastUpdatedDate ? (
              <TextFormat value={serviceOrderEntity.lastUpdatedDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
        </dl>
        <Button tag={Link} to="/service-order" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/service-order/${serviceOrderEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ serviceOrder }: IRootState) => ({
  serviceOrderEntity: serviceOrder.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ServiceOrderDetail);
