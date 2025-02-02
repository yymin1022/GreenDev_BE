package com.devoceanyoung.greendev.domain.campaign.dto;

import javax.validation.constraints.NotBlank;

import com.devoceanyoung.greendev.domain.campaign.domain.Campaign;
import com.devoceanyoung.greendev.domain.member.domain.Member;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CampaignReqDto {
	@NotBlank(message = "제목은 필수로 입력되어야 합니다.")
	private String title;

	private String description;
	private String date;
	private String category;
	private String imageUrl;

	@Builder
	public CampaignReqDto(String title, String description, String date, String category, String imageUrl) {
		this.title = title;
		this.description = description;
		this.date = date;
		this.category = category;
		this.imageUrl = imageUrl;
	}

	public Campaign toEntity(Member member){
		return Campaign.builder()
			.title(title)
			.description(description)
			.date(date)
			.category(category)
			.campaignimageUrl(imageUrl)
			.member(member)
			.build();
	}
}

