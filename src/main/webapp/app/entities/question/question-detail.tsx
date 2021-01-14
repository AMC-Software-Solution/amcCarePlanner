import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './question.reducer';
import { IQuestion } from 'app/shared/model/question.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IQuestionDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const QuestionDetail = (props: IQuestionDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { questionEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="carePlannerApp.question.detail.title">Question</Translate> [<b>{questionEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="question">
              <Translate contentKey="carePlannerApp.question.question">Question</Translate>
            </span>
          </dt>
          <dd>{questionEntity.question}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="carePlannerApp.question.description">Description</Translate>
            </span>
          </dt>
          <dd>{questionEntity.description}</dd>
          <dt>
            <span id="attribute1">
              <Translate contentKey="carePlannerApp.question.attribute1">Attribute 1</Translate>
            </span>
          </dt>
          <dd>{questionEntity.attribute1}</dd>
          <dt>
            <span id="attribute2">
              <Translate contentKey="carePlannerApp.question.attribute2">Attribute 2</Translate>
            </span>
          </dt>
          <dd>{questionEntity.attribute2}</dd>
          <dt>
            <span id="attribute3">
              <Translate contentKey="carePlannerApp.question.attribute3">Attribute 3</Translate>
            </span>
          </dt>
          <dd>{questionEntity.attribute3}</dd>
          <dt>
            <span id="attribute4">
              <Translate contentKey="carePlannerApp.question.attribute4">Attribute 4</Translate>
            </span>
          </dt>
          <dd>{questionEntity.attribute4}</dd>
          <dt>
            <span id="attribute5">
              <Translate contentKey="carePlannerApp.question.attribute5">Attribute 5</Translate>
            </span>
          </dt>
          <dd>{questionEntity.attribute5}</dd>
          <dt>
            <span id="optional">
              <Translate contentKey="carePlannerApp.question.optional">Optional</Translate>
            </span>
          </dt>
          <dd>{questionEntity.optional ? 'true' : 'false'}</dd>
          <dt>
            <span id="createdDate">
              <Translate contentKey="carePlannerApp.question.createdDate">Created Date</Translate>
            </span>
          </dt>
          <dd>
            {questionEntity.createdDate ? <TextFormat value={questionEntity.createdDate} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="lastUpdatedDate">
              <Translate contentKey="carePlannerApp.question.lastUpdatedDate">Last Updated Date</Translate>
            </span>
          </dt>
          <dd>
            {questionEntity.lastUpdatedDate ? (
              <TextFormat value={questionEntity.lastUpdatedDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="clientId">
              <Translate contentKey="carePlannerApp.question.clientId">Client Id</Translate>
            </span>
          </dt>
          <dd>{questionEntity.clientId}</dd>
          <dt>
            <span id="hasExtraData">
              <Translate contentKey="carePlannerApp.question.hasExtraData">Has Extra Data</Translate>
            </span>
          </dt>
          <dd>{questionEntity.hasExtraData ? 'true' : 'false'}</dd>
        </dl>
        <Button tag={Link} to="/question" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/question/${questionEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ question }: IRootState) => ({
  questionEntity: question.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(QuestionDetail);
