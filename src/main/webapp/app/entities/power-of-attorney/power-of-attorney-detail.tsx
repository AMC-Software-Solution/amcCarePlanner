import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './power-of-attorney.reducer';
import { IPowerOfAttorney } from 'app/shared/model/power-of-attorney.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IPowerOfAttorneyDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const PowerOfAttorneyDetail = (props: IPowerOfAttorneyDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { powerOfAttorneyEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="carePlannerApp.powerOfAttorney.detail.title">PowerOfAttorney</Translate> [<b>{powerOfAttorneyEntity.id}</b>
          ]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="powerOfAttorneyConsent">
              <Translate contentKey="carePlannerApp.powerOfAttorney.powerOfAttorneyConsent">Power Of Attorney Consent</Translate>
            </span>
          </dt>
          <dd>{powerOfAttorneyEntity.powerOfAttorneyConsent ? 'true' : 'false'}</dd>
          <dt>
            <span id="healthAndWelfare">
              <Translate contentKey="carePlannerApp.powerOfAttorney.healthAndWelfare">Health And Welfare</Translate>
            </span>
          </dt>
          <dd>{powerOfAttorneyEntity.healthAndWelfare ? 'true' : 'false'}</dd>
          <dt>
            <span id="healthAndWelfareName">
              <Translate contentKey="carePlannerApp.powerOfAttorney.healthAndWelfareName">Health And Welfare Name</Translate>
            </span>
          </dt>
          <dd>{powerOfAttorneyEntity.healthAndWelfareName}</dd>
          <dt>
            <span id="propertyAndFinAffairs">
              <Translate contentKey="carePlannerApp.powerOfAttorney.propertyAndFinAffairs">Property And Fin Affairs</Translate>
            </span>
          </dt>
          <dd>{powerOfAttorneyEntity.propertyAndFinAffairs ? 'true' : 'false'}</dd>
          <dt>
            <span id="propertyAndFinAffairsName">
              <Translate contentKey="carePlannerApp.powerOfAttorney.propertyAndFinAffairsName">Property And Fin Affairs Name</Translate>
            </span>
          </dt>
          <dd>{powerOfAttorneyEntity.propertyAndFinAffairsName}</dd>
          <dt>
            <span id="createdDate">
              <Translate contentKey="carePlannerApp.powerOfAttorney.createdDate">Created Date</Translate>
            </span>
          </dt>
          <dd>
            {powerOfAttorneyEntity.createdDate ? (
              <TextFormat value={powerOfAttorneyEntity.createdDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="lastUpdatedDate">
              <Translate contentKey="carePlannerApp.powerOfAttorney.lastUpdatedDate">Last Updated Date</Translate>
            </span>
          </dt>
          <dd>
            {powerOfAttorneyEntity.lastUpdatedDate ? (
              <TextFormat value={powerOfAttorneyEntity.lastUpdatedDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="clientId">
              <Translate contentKey="carePlannerApp.powerOfAttorney.clientId">Client Id</Translate>
            </span>
          </dt>
          <dd>{powerOfAttorneyEntity.clientId}</dd>
          <dt>
            <span id="hasExtraData">
              <Translate contentKey="carePlannerApp.powerOfAttorney.hasExtraData">Has Extra Data</Translate>
            </span>
          </dt>
          <dd>{powerOfAttorneyEntity.hasExtraData ? 'true' : 'false'}</dd>
          <dt>
            <Translate contentKey="carePlannerApp.powerOfAttorney.serviceUser">Service User</Translate>
          </dt>
          <dd>{powerOfAttorneyEntity.serviceUserServiceUserCode ? powerOfAttorneyEntity.serviceUserServiceUserCode : ''}</dd>
          <dt>
            <Translate contentKey="carePlannerApp.powerOfAttorney.witnessedBy">Witnessed By</Translate>
          </dt>
          <dd>{powerOfAttorneyEntity.witnessedByEmployeeCode ? powerOfAttorneyEntity.witnessedByEmployeeCode : ''}</dd>
        </dl>
        <Button tag={Link} to="/power-of-attorney" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/power-of-attorney/${powerOfAttorneyEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ powerOfAttorney }: IRootState) => ({
  powerOfAttorneyEntity: powerOfAttorney.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(PowerOfAttorneyDetail);
