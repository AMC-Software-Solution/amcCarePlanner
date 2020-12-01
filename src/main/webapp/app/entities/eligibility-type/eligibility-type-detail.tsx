import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './eligibility-type.reducer';
import { IEligibilityType } from 'app/shared/model/eligibility-type.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IEligibilityTypeDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const EligibilityTypeDetail = (props: IEligibilityTypeDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { eligibilityTypeEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="carePlannerApp.eligibilityType.detail.title">EligibilityType</Translate> [<b>{eligibilityTypeEntity.id}</b>
          ]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="eligibilityType">
              <Translate contentKey="carePlannerApp.eligibilityType.eligibilityType">Eligibility Type</Translate>
            </span>
          </dt>
          <dd>{eligibilityTypeEntity.eligibilityType}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="carePlannerApp.eligibilityType.description">Description</Translate>
            </span>
          </dt>
          <dd>{eligibilityTypeEntity.description}</dd>
        </dl>
        <Button tag={Link} to="/eligibility-type" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/eligibility-type/${eligibilityTypeEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ eligibilityType }: IRootState) => ({
  eligibilityTypeEntity: eligibilityType.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(EligibilityTypeDetail);
