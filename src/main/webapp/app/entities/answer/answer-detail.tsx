import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './answer.reducer';
import { IAnswer } from 'app/shared/model/answer.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IAnswerDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const AnswerDetail = (props: IAnswerDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { answerEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="carePlannerApp.answer.detail.title">Answer</Translate> [<b>{answerEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="answer">
              <Translate contentKey="carePlannerApp.answer.answer">Answer</Translate>
            </span>
          </dt>
          <dd>{answerEntity.answer}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="carePlannerApp.answer.description">Description</Translate>
            </span>
          </dt>
          <dd>{answerEntity.description}</dd>
          <dt>
            <span id="attribute1">
              <Translate contentKey="carePlannerApp.answer.attribute1">Attribute 1</Translate>
            </span>
          </dt>
          <dd>{answerEntity.attribute1}</dd>
          <dt>
            <span id="attribute2">
              <Translate contentKey="carePlannerApp.answer.attribute2">Attribute 2</Translate>
            </span>
          </dt>
          <dd>{answerEntity.attribute2}</dd>
          <dt>
            <span id="attribute3">
              <Translate contentKey="carePlannerApp.answer.attribute3">Attribute 3</Translate>
            </span>
          </dt>
          <dd>{answerEntity.attribute3}</dd>
          <dt>
            <span id="attribute4">
              <Translate contentKey="carePlannerApp.answer.attribute4">Attribute 4</Translate>
            </span>
          </dt>
          <dd>{answerEntity.attribute4}</dd>
          <dt>
            <span id="attribute5">
              <Translate contentKey="carePlannerApp.answer.attribute5">Attribute 5</Translate>
            </span>
          </dt>
          <dd>{answerEntity.attribute5}</dd>
          <dt>
            <span id="createdDate">
              <Translate contentKey="carePlannerApp.answer.createdDate">Created Date</Translate>
            </span>
          </dt>
          <dd>{answerEntity.createdDate ? <TextFormat value={answerEntity.createdDate} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="lastUpdatedDate">
              <Translate contentKey="carePlannerApp.answer.lastUpdatedDate">Last Updated Date</Translate>
            </span>
          </dt>
          <dd>
            {answerEntity.lastUpdatedDate ? <TextFormat value={answerEntity.lastUpdatedDate} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="tenantId">
              <Translate contentKey="carePlannerApp.answer.tenantId">Tenant Id</Translate>
            </span>
          </dt>
          <dd>{answerEntity.tenantId}</dd>
          <dt>
            <Translate contentKey="carePlannerApp.answer.question">Question</Translate>
          </dt>
          <dd>{answerEntity.questionQuestion ? answerEntity.questionQuestion : ''}</dd>
          <dt>
            <Translate contentKey="carePlannerApp.answer.serviceUser">Service User</Translate>
          </dt>
          <dd>{answerEntity.serviceUserServiceUserCode ? answerEntity.serviceUserServiceUserCode : ''}</dd>
          <dt>
            <Translate contentKey="carePlannerApp.answer.recordedBy">Recorded By</Translate>
          </dt>
          <dd>{answerEntity.recordedByEmployeeCode ? answerEntity.recordedByEmployeeCode : ''}</dd>
        </dl>
        <Button tag={Link} to="/answer" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/answer/${answerEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ answer }: IRootState) => ({
  answerEntity: answer.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(AnswerDetail);
