//
//  PokeImage.swift
//  PokeDexCombine
//
//  Created by parris.person on 21/10/24.
//

import SwiftUI
import SDWebImageSwiftUI

struct PokemonImage: View {
    let imageUrl: String
    let size: CGFloat

    var body: some View {
        WebImage(url: URL(string: imageUrl)) { image in
            image.resizable()
        } placeholder: {
            Image("poke")
                .resizable()
                .frame(width: size, height: size)
        }
        .onSuccess { image, data, cacheType in
          
        }
        .indicator(.activity)
        .transition(.fade(duration: 0.5)) 
        .scaledToFit()
        .frame(width: size, height: size, alignment: .center)
    }
}
