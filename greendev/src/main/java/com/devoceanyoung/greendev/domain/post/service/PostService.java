package com.devoceanyoung.greendev.domain.post.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devoceanyoung.greendev.domain.campaign.domain.Campaign;
import com.devoceanyoung.greendev.domain.campaign.service.CampaignService;
import com.devoceanyoung.greendev.domain.member.domain.Member;
import com.devoceanyoung.greendev.domain.post.domain.Post;
import com.devoceanyoung.greendev.domain.post.dto.PostReqDto;
import com.devoceanyoung.greendev.domain.post.exception.PostNotFoundException;
import com.devoceanyoung.greendev.domain.post.repository.PostRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class PostService {
	private final PostRepository postRepository;
	private final CampaignService campaignService;

	public Long create(Member member, Long campaignId, PostReqDto postReqDto) {

		Campaign campaign = campaignService.findById(campaignId);
		Post post = postRepository.save(postReqDto.toEntity(member, campaign));
		campaign.addPost(post);
		return post.getPostId();
	}

	public void update(Long postId, PostReqDto postReqDto) {
		Post post = findById(postId);
		post.updateContent(postReqDto);

	}
	public void delete(Long postId) {
		Post post = findById(postId);
		postRepository.delete(post);

	}

	@Transactional(readOnly = true)
	public Page<Post> getPostsByCampaign(Long campaignId, Pageable pageable) {
		Campaign campaign = campaignService.findById(campaignId);
		return postRepository.findByCampaign(campaign, pageable);
	}

	@Transactional(readOnly = true)
	public Post findById(Long postId) {
		return postRepository.findById(postId)
			.orElseThrow(PostNotFoundException::new);
	}

	@Transactional(readOnly = true)
	public List<Post> findByCampaign(Campaign campaign) {
		return postRepository.findAllByCampaign(campaign);
	}

}