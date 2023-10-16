package com.bridgelab.funapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bridgelab.funapp.dto.NoteDTO;
import com.bridgelab.funapp.exception.FunToCustomException;
import com.bridgelab.funapp.exception.UserCustomException;
import com.bridgelab.funapp.model.Notes;
import com.bridgelab.funapp.model.User;
import com.bridgelab.funapp.repository.NoteRepository;
import com.bridgelab.funapp.repository.UserRepository;
import com.bridgelab.funapp.util.JwtToken;

import jakarta.validation.Valid;

@Service
public class NoteService {
	
	@Autowired
	NoteRepository trashSystemRepository;
	
	@Autowired
	UserRepository userRepository;
	
	 @Autowired
		private JwtToken jwtToken;
	
	public Notes createNote(String token ,  NoteDTO note) {
		
		//Long userIdParseLong = Long.parseLong(userId);
		long id =jwtToken.decodeToken(token);
		 User user_Data = userRepository.findById(id)
				.orElseThrow(() -> new UserCustomException("User with id not found " + id));
		Notes notes = new Notes(note , user_Data);
		return trashSystemRepository.save(notes);
	}


	public Notes getById(String token, long id) {
		long userId =jwtToken.decodeToken(token);
		 User user_Data = userRepository.findById(userId)
					.orElseThrow(() -> new UserCustomException("User with id not found " + userId));
		return trashSystemRepository.findById(id)
				.orElseThrow(() -> new FunToCustomException("Notes with id:"+id+" not found"));
	}


	public void deleteNotes(String token, long id) {
		Notes note_Data = getById(token ,id);
		if(note_Data != null) {
			
			trashSystemRepository.deleteById(id);
	}

  }

	public Notes updateNotes(String token, long id, NoteDTO note) {
		// TODO Auto-generated method stub
		Notes note_Data = getById(token ,id);
		if(note_Data != null) {
			note_Data.setTitle(note.title);
			note_Data.setDescription(note.description);
			return trashSystemRepository.save(note_Data);
		}
		
		return null;
	}

	public List<Notes> getAllNotes() {
		// TODO Auto-generated method stub
		return trashSystemRepository.findAll();
	}


	public Notes updateArchievd(String token, long id) {
		// TODO Auto-generated method stub
		Notes note_Data = getById(token, id);
		if(note_Data != null) {
			note_Data.setArchievd(true);
			return trashSystemRepository.save(note_Data);
		}
		return null;
	}


	public Notes updateTrash(String token, long id) {
		// TODO Auto-generated method stub
		Notes note_Data = getById(token, id);
		if(note_Data != null) {
			note_Data.setTrash(true);
			return trashSystemRepository.save(note_Data);
		}
		return null;
	}


	public Notes updateUnarchievd(String token,long id) {
		// TODO Auto-generated method stub
		Notes note_Data = getById(token,id);
		if(note_Data != null) {
			note_Data.setArchievd(false);
			return trashSystemRepository.save(note_Data);
		}
		return null;
	}


	public Notes updateUntrash(String token, long id) {
		// TODO Auto-generated method stub
		Notes note_Data = getById(token, id);
		if(note_Data != null) {
			note_Data.setTrash(false);
			return trashSystemRepository.save(note_Data);
		}
		return null;
	}


	public Optional<Notes> getByUserId(String token) {
		// TODO Auto-generated method stub
		long id =jwtToken.decodeToken(token);
		//if(id != 0)
		
			return trashSystemRepository.findByUserId(id);
		
		//return Optional.empty();
	}

	public List<Notes> getNotesByLabel(String label) {
		// TODO Auto-generated method stub
		return trashSystemRepository.findNotesByLabel(label);
	}


	public List<String> getLabelsByNoteId(long noteId) {
		// TODO Auto-generated method stub
		List<String> ls=trashSystemRepository.findLabelsByNoteId(noteId);
		System.out.println(ls);
		return ls;
	}

}