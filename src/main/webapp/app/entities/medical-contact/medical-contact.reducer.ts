import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IMedicalContact, defaultValue } from 'app/shared/model/medical-contact.model';

export const ACTION_TYPES = {
  FETCH_MEDICALCONTACT_LIST: 'medicalContact/FETCH_MEDICALCONTACT_LIST',
  FETCH_MEDICALCONTACT: 'medicalContact/FETCH_MEDICALCONTACT',
  CREATE_MEDICALCONTACT: 'medicalContact/CREATE_MEDICALCONTACT',
  UPDATE_MEDICALCONTACT: 'medicalContact/UPDATE_MEDICALCONTACT',
  DELETE_MEDICALCONTACT: 'medicalContact/DELETE_MEDICALCONTACT',
  RESET: 'medicalContact/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IMedicalContact>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type MedicalContactState = Readonly<typeof initialState>;

// Reducer

export default (state: MedicalContactState = initialState, action): MedicalContactState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_MEDICALCONTACT_LIST):
    case REQUEST(ACTION_TYPES.FETCH_MEDICALCONTACT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_MEDICALCONTACT):
    case REQUEST(ACTION_TYPES.UPDATE_MEDICALCONTACT):
    case REQUEST(ACTION_TYPES.DELETE_MEDICALCONTACT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_MEDICALCONTACT_LIST):
    case FAILURE(ACTION_TYPES.FETCH_MEDICALCONTACT):
    case FAILURE(ACTION_TYPES.CREATE_MEDICALCONTACT):
    case FAILURE(ACTION_TYPES.UPDATE_MEDICALCONTACT):
    case FAILURE(ACTION_TYPES.DELETE_MEDICALCONTACT):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_MEDICALCONTACT_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_MEDICALCONTACT):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_MEDICALCONTACT):
    case SUCCESS(ACTION_TYPES.UPDATE_MEDICALCONTACT):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_MEDICALCONTACT):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {},
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState,
      };
    default:
      return state;
  }
};

const apiUrl = 'api/medical-contacts';

// Actions

export const getEntities: ICrudGetAllAction<IMedicalContact> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_MEDICALCONTACT_LIST,
    payload: axios.get<IMedicalContact>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IMedicalContact> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_MEDICALCONTACT,
    payload: axios.get<IMedicalContact>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IMedicalContact> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_MEDICALCONTACT,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IMedicalContact> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_MEDICALCONTACT,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IMedicalContact> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_MEDICALCONTACT,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
