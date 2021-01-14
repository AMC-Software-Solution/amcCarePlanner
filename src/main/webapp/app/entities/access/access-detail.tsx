import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './access.reducer';
import { IAccess } from 'app/shared/model/access.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IAccessDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const AccessDetail = (props: IAccessDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { accessEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="carePlannerApp.access.detail.title">Access</Translate> [<b>{accessEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="keySafeNumber">
              <Translate contentKey="carePlannerApp.access.keySafeNumber">Key Safe Number</Translate>
            </span>
          </dt>
          <dd>{accessEntity.keySafeNumber}</dd>
          <dt>
            <span id="accessDetails">
              <Translate contentKey="carePlannerApp.access.accessDetails">Access Details</Translate>
            </span>
          </dt>
          <dd>{accessEntity.accessDetails}</dd>
          <dt>
            <span id="createdDate">
              <Translate contentKey="carePlannerApp.access.createdDate">Created Date</Translate>
            </span>
          </dt>
          <dd>{accessEntity.createdDate ? <TextFormat value={accessEntity.createdDate} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="lastUpdatedDate">
              <Translate contentKey="carePlannerApp.access.lastUpdatedDate">Last Updated Date</Translate>
            </span>
          </dt>
          <dd>
            {accessEntity.lastUpdatedDate ? <TextFormat value={accessEntity.lastUpdatedDate} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="clientId">
              <Translate contentKey="carePlannerApp.access.clientId">Client Id</Translate>
            </span>
          </dt>
          <dd>{accessEntity.clientId}</dd>
          <dt>
            <span id="hasExtraData">
              <Translate contentKey="carePlannerApp.access.hasExtraData">Has Extra Data</Translate>
            </span>
          </dt>
          <dd>{accessEntity.hasExtraData ? 'true' : 'false'}</dd>
          <dt>
            <Translate contentKey="carePlannerApp.access.serviceUser">Service User</Translate>
          </dt>
          <dd>{accessEntity.serviceUserServiceUserCode ? accessEntity.serviceUserServiceUserCode : ''}</dd>
        </dl>
        <Button tag={Link} to="/access" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/access/${accessEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ access }: IRootState) => ({
  accessEntity: access.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(AccessDetail);
