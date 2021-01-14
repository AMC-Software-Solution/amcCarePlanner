import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './equality.reducer';
import { IEquality } from 'app/shared/model/equality.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IEqualityDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const EqualityDetail = (props: IEqualityDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { equalityEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="carePlannerApp.equality.detail.title">Equality</Translate> [<b>{equalityEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="gender">
              <Translate contentKey="carePlannerApp.equality.gender">Gender</Translate>
            </span>
          </dt>
          <dd>{equalityEntity.gender}</dd>
          <dt>
            <span id="maritalStatus">
              <Translate contentKey="carePlannerApp.equality.maritalStatus">Marital Status</Translate>
            </span>
          </dt>
          <dd>{equalityEntity.maritalStatus}</dd>
          <dt>
            <span id="religion">
              <Translate contentKey="carePlannerApp.equality.religion">Religion</Translate>
            </span>
          </dt>
          <dd>{equalityEntity.religion}</dd>
          <dt>
            <span id="createdDate">
              <Translate contentKey="carePlannerApp.equality.createdDate">Created Date</Translate>
            </span>
          </dt>
          <dd>
            {equalityEntity.createdDate ? <TextFormat value={equalityEntity.createdDate} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="lastUpdatedDate">
              <Translate contentKey="carePlannerApp.equality.lastUpdatedDate">Last Updated Date</Translate>
            </span>
          </dt>
          <dd>
            {equalityEntity.lastUpdatedDate ? (
              <TextFormat value={equalityEntity.lastUpdatedDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="clientId">
              <Translate contentKey="carePlannerApp.equality.clientId">Client Id</Translate>
            </span>
          </dt>
          <dd>{equalityEntity.clientId}</dd>
          <dt>
            <span id="hasExtraData">
              <Translate contentKey="carePlannerApp.equality.hasExtraData">Has Extra Data</Translate>
            </span>
          </dt>
          <dd>{equalityEntity.hasExtraData ? 'true' : 'false'}</dd>
          <dt>
            <Translate contentKey="carePlannerApp.equality.nationality">Nationality</Translate>
          </dt>
          <dd>{equalityEntity.nationalityCountryName ? equalityEntity.nationalityCountryName : ''}</dd>
          <dt>
            <Translate contentKey="carePlannerApp.equality.serviceUser">Service User</Translate>
          </dt>
          <dd>{equalityEntity.serviceUserServiceUserCode ? equalityEntity.serviceUserServiceUserCode : ''}</dd>
        </dl>
        <Button tag={Link} to="/equality" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/equality/${equalityEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ equality }: IRootState) => ({
  equalityEntity: equality.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(EqualityDetail);
