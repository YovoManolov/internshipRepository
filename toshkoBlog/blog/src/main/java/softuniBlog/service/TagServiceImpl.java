package softuniBlog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuniBlog.entity.Tag;
import softuniBlog.repository.TagRepository;

/**
 * Created by todor on 2.8.2017 г..
 */

@Service
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;

    @Autowired
    public TagServiceImpl(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }


    @Override
    public Tag getTagByName(String name) {
        return this.tagRepository.findByName(name);
    }
}
